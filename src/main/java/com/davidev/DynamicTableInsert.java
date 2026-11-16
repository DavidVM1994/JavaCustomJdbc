package com.davidev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicTableInsert {
    private static final Logger log = LoggerFactory.getLogger(DynamicTableInsert.class);

    private static String sqlBuilder(String schema, String table, String[] columns){
        if (schema == null || schema.isBlank()){
            throw new IllegalStateException("Missing required table schema");
        }
        if (table == null || table.isBlank()){
            throw new IllegalStateException("Missing required table name");
        }
        if (columns == null || columns.length == 0){
            throw new IllegalStateException("Missing required table columns");
        }
        String qualifiedTableName = schema + "." + table;
        String cols = String.join(", ", columns);
        String placeHolders = String.join(", ", Collections.nCopies(columns.length, "?"));
        return "INSERT INTO " + qualifiedTableName + "(" + cols + ")" + " VALUES (" + placeHolders + ")";
    }

    public static InsertResult insertMagmaRecords(
            String url,
            String username,
            String password,
            String schema,
            String table,
            String fileName,
            List<DataLine> dataLines
    ) throws SQLException {
        if (dataLines == null || dataLines.isEmpty()){
            return new InsertResult(0,0);
        }
        log.info("Started insert on table:{}.{} | number of records: {}",schema, table, dataLines.size());
        long startMillis = System.currentTimeMillis();
        String[] columns = {"SIL_FILENAME", "SIL_LINE_NUMBER", "SIL_LINE_CONTENT", "SIL_PROCESSED"};
        String sqlString = sqlBuilder(schema, table, columns);
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);
            log.info("connection to database successfully established");
            try(PreparedStatement ps = connection.prepareStatement(sqlString)) {
                for (DataLine line : dataLines) {
                    ps.setString(1, fileName);
                    ps.setInt(2, line.getSilLineNumber() != null ? line.getSilLineNumber() : 0);
                    ps.setString(3, line.getSilLineContent());
                    ps.setInt(4, line.getSilProcess() != null ? line.getSilProcess() : 0);
                    ps.addBatch();
                }
                int[] count = ps.executeBatch();
                connection.commit();
                int total = Arrays.stream(count).sum();
                long durationMillis = System.currentTimeMillis() - startMillis;
                log.info("Insert Completed | record inserted: {} | duration_ms: {}", total, durationMillis);
                return new InsertResult(total, durationMillis);
            }catch (SQLException e){
                log.error("Insert failed for {}.{}, rolling back", schema, table, e);
                connection.rollback();
                throw e;
            }
        }
        catch (SQLException e) {
            log.error("Database error while inserting into {}.{}", schema, table, e);
            throw e;
        }
    }
}
