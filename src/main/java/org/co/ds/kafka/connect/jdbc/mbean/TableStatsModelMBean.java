package org.co.ds.kafka.connect.jdbc.mbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import javax.management.modelmbean.*;

public class TableStatsModelMBean {
    private static final Logger log = LoggerFactory.getLogger(TableStatsModelMBean.class);
    private DescriptorSupport mmbDesc;
    // internal variables describing the MBean
    private String dClassName = "TableStats";
    private String dDescription = "Simple implementation of a Model dynamic MBean.";


    // Model implemenation
    private ModelMBeanAttributeInfo[] dAttributes = new ModelMBeanAttributeInfo[5];
    private ModelMBeanConstructorInfo[] dConstructors = new ModelMBeanConstructorInfo[0];
    private ModelMBeanOperationInfo[] dOperations = new ModelMBeanOperationInfo[5];
    private ModelMBeanNotificationInfo[] dNotifications = new ModelMBeanNotificationInfo[0];
    private ModelMBeanInfo dMBeanInfo = null;

    private Descriptor setDescriptors(String name, String displayName, String getMethod, String currencyTimeLimit, String setMethod) {
        Descriptor desc = new DescriptorSupport();
        desc.setField("name", name);
        desc.setField("descriptorType", "attribute");
        desc.setField("displayName", displayName);
        desc.setField("getMethod", getMethod);
        desc.setField("setMethod", setMethod);
        desc.setField("currencyTimeLimit", currencyTimeLimit);

        return desc;
    }

    public ModelMBeanInfo buildModelMBeanInfo(
            ObjectName inMbeanObjectName, String inMbeanName) {
        try {

            // Create the descriptor and ModelMBeanAttributeInfo
            // for the 1st attribute
            //
            Descriptor tableNameDesc = setDescriptors("tableName",
                    "tableName",
                    "getTableName",
                    "20",
                    "setTableName");

            Descriptor startTsDesc = setDescriptors("startTs",
                    "startTs",
                    "getStartTs",
                    "20",
                    "setStartTs");

            Descriptor countDesc = setDescriptors("counter",
                    "counter",
                    "getCounter",
                    "20",
                    "setCounter");

            Descriptor endTsDesc = setDescriptors("endTs",
                    "endTs",
                    "getEndTs",
                    "20",
                    "setEndTs");

            Descriptor statusDesc = setDescriptors("status",
                    "status",
                    "getStatus",
                    "20",
                    "setStatus");

            dAttributes[0] = new ModelMBeanAttributeInfo(
                    "tableName",
                    "java.lang.String",
                    "JDBC Source Connector Name",
                    true,
                    true,
                    false,
                    tableNameDesc);

            dAttributes[1] = new ModelMBeanAttributeInfo(
                    "startTs",
                    "java.lang.Long",
                    "Batch Start Timestamp",
                    true,
                    true,
                    false,
                    startTsDesc);

            dAttributes[2] = new ModelMBeanAttributeInfo(
                    "counter",
                    "java.lang.Long",
                    "Message count",
                    true,
                    true,
                    false,
                    countDesc);

            dAttributes[3] = new ModelMBeanAttributeInfo(
                    "endTs",
                    "java.lang.Long",
                    "Batch End Timestamp",
                    true,
                    true,
                    false,
                    endTsDesc);

            dAttributes[4] = new ModelMBeanAttributeInfo(
                    "status",
                    "java.lang.String",
                    "Status",
                    true,
                    true,
                    false,
                    statusDesc);


            // use reflection to get constructor signatures
            //Constructor[] constructors = "TableStats".getClass().getConstructors();
            //dConstructors[0] = new ModelMBeanConstructorInfo(
            //        "TableStats(): No-parameter constructor",  //description
            //        constructors[0]);                  // the contructor object

            MBeanParameterInfo[] params = null;
            dOperations[0] = new ModelMBeanOperationInfo(
                    "getTableName",
                    "Fetch table name",
                    params,
                    "String",
                    MBeanOperationInfo.ACTION);

            dOperations[1] = new ModelMBeanOperationInfo(
                    "getStartTs",
                    "Fetch table name",
                    params,
                    "Long",
                    MBeanOperationInfo.ACTION);

            dOperations[2] = new ModelMBeanOperationInfo(
                    "getCounter",
                    "Fetch table name",
                    params,
                    "Long",
                    MBeanOperationInfo.ACTION);

            dOperations[3] = new ModelMBeanOperationInfo(
                    "getEndTs",
                    "Fetch table name",
                    params,
                    "Long",
                    MBeanOperationInfo.ACTION);

            dOperations[4] = new ModelMBeanOperationInfo(
                    "getStatus",
                    "Fetch table name",
                    params,
                    "String",
                    MBeanOperationInfo.ACTION);


            mmbDesc = new DescriptorSupport(new String[]
                    {("name=" + inMbeanObjectName),
                            "descriptorType=mbean",
                            ("displayName=" + inMbeanName),
                            "log=T",
                            "logfile=jmxmain.log",
                            "currencyTimeLimit=5"});

            // Create the ModelMBeanInfo for the whole MBean
            //
            dMBeanInfo = new ModelMBeanInfoSupport(
                    dClassName,
                    dDescription,
                    dAttributes,
                    dConstructors,
                    dOperations,
                    dNotifications);

            dMBeanInfo.setMBeanDescriptor(mmbDesc);

        } catch (Exception e) {
            log.error("\nException in buildModelMBeanInfo : " +
                    e.getMessage());
            e.printStackTrace();
        }

        return dMBeanInfo;
    }



}
