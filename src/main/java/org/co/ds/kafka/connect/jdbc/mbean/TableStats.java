package org.co.ds.kafka.connect.jdbc.mbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableStats implements java.io.Serializable{
    private static final Logger log = LoggerFactory.getLogger(TableStats.class);
    private  String tableName = "default";
    private  Long startTs = 0L;
    private  Long counter = 0L;
    private Long endTs = 0L;

    private String status = "INIT";

    public TableStats() {
        log.info("\tNo-args TestBean Constructor Invoked: tableName " +
                tableName + " startTs: " + startTs + " counter: " + counter +
                " endTs: " + endTs + " status: " + status);
    }

    public TableStats(String tableName) {
        this.tableName = tableName;
        this.startTs =  System.currentTimeMillis();
        this.endTs = 0L;
        this.status = "STARTED";

        log.info("\tTestBean Constructor Invoked: tableName: " +
                tableName + " startTs: " + startTs  + " counter: " + counter +
                " endTs: " + endTs + " status: " + status);
            }

    public void closeTableStats() {
        this.endTs =   System.currentTimeMillis();
        this.status = "COMPLETED";
        log.info("\tcloseTableStats Invoked: tableName: " +
                tableName + " startTs: " + startTs +  " counter: " + counter +
                " endTs: " + endTs + " status: " + status);

    }

    public String getTableName() {
        return tableName;
    }


    public Long getStartTs() {
        log.info("\tgetStartTs Invoked: startTs: " + startTs);
        return startTs;
    }

    public Long getCounter() {
        log.info("\tgetCounter Invoked: counter: " + counter);
        return counter;
    }

    public Long getEndTs() {
        log.info("\tgetEndTs Invoked: endTs: " + endTs);
        return endTs;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setStartTs(long startTs) {
        this.startTs = startTs;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public void setEndTs(long endTs) {
        this.endTs = endTs;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void incrementCounter(){
        log.info("\tincrementCounter Invoked: counter: " + counter);
        this.counter++;

    }

    public String getStatus() {

        log.info("\tgetStatus Invoked: startTs: " + status);
        return status;
    }
}
