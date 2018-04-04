package topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import sbolt.EndSblotImpl;
import sbolt.SboltImpl;
import spout.SpoutImpl;

public class AppRunner {
    public static void main(String[] args) throws InterruptedException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader",new SpoutImpl());
        builder.setBolt("word-normalizer",new SboltImpl()).shuffleGrouping("word-reader");
        builder.setBolt("word-counter",new EndSblotImpl()).fieldsGrouping("word-normalizer",new Fields("word"));

        StormTopology topology = builder.createTopology();
        Config conf = new Config();
        String fileName ="word.txt" ;
        conf.put("fileName" , fileName );
        conf.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Topologie" , conf , topology );
        Thread. sleep(5000);
        cluster.shutdown();
    }
}
