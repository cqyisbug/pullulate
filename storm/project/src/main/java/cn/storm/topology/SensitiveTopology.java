package cn.storm.topology;

import cn.storm.bean.RubbishUsers;
import cn.storm.bolt.SensitiveBatchBolt;
import cn.storm.bolt.SensitiveFileAnalyzer;
import cn.storm.spout.SensitiveFileReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.storm.Config;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class SensitiveTopology {
    public static final String SENSITIVE_SPOUT_1 = "sensitiveSpout591";
    public static final String SENSITIVE_SPOUT_2 = "sensitiveSpout592";

    public static final String SensitiveBoltAnalysis = "SensitiveBoltAnalysis";
    public static final String SensitiveBoltPersistence = "SensitiveBolPersistence";

    public static void main(String[] args) throws AuthorizationException {
        System.out.println(StringUtils.center("SensitiveTopology", 40, "*"));

        TopologyBuilder builder = new TopologyBuilder();

        // 构建spout，分别设置并行度为2
        builder.setSpout(SENSITIVE_SPOUT_1, new SensitiveFileReader(SensitiveFileReader.INPUT_FILE_PATH_1), 2);
        builder.setSpout(SENSITIVE_SPOUT_2, new SensitiveFileReader(SensitiveFileReader.INPUT_FILE_PATH_2), 2);

        // 构建bolt设置并行度为4
        builder.setBolt(SensitiveBoltAnalysis, new SensitiveFileAnalyzer(), 4)
                .shuffleGrouping(SENSITIVE_SPOUT_1)
                .shuffleGrouping(SENSITIVE_SPOUT_2);

        // 构建bolt设置并行度为4
        SensitiveBatchBolt persistenceBolt = new SensitiveBatchBolt();

        builder.setBolt(SensitiveBoltPersistence, persistenceBolt, 4)
                .fieldsGrouping(
                        SensitiveBoltAnalysis,
                        new Fields(RubbishUsers.HOMECITY_COLUMNNAME,
                                RubbishUsers.USERID_COLUMNNAME,
                                RubbishUsers.MSISDN_COLUMNNAME));

        Config conf = new Config();
        conf.setDebug(true);
        // 设置worker，集群里面最大就8个slots了，全部使用上
        conf.setNumWorkers(8);
        // 3秒监控一次敏感信息入库MySQL情况
        conf.put("RUBBISHMONITOR_INTERVAL", 3);

        // 执行分布式拓扑
        try {
            StormRunner.runTopologyRemotely(builder.createTopology(), "SensitiveTopology", conf);
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        }
    }
}
