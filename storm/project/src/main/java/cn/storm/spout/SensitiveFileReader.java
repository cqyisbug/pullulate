package cn.storm.spout;

import org.apache.storm.shade.org.apache.commons.io.FileUtils;
import org.apache.storm.shade.org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SensitiveFileReader extends BaseRichSpout {

    public static final String INPUT_FILE_PATH_1 = "/home/tj/data/591";
    public static final String INPUT_FILE_PATH_2 = "/home/tj/data/592";

    public static final String FINISH_FILE_SUFFIX = ".bak";

    private String sensitiveFilePath = "";
    private SpoutOutputCollector collector;



    public SensitiveFileReader(String sensitiveFilePath) {
        this.sensitiveFilePath = sensitiveFilePath;
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        Collection<File> files = FileUtils.listFiles(new File(sensitiveFilePath), FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(FINISH_FILE_SUFFIX)), null);
        for (File f : files) {
            try {
                List<String> lines = FileUtils.readLines(f,"UTF-8");
                for(String line: lines){
                    System.out.println("[SensitiveTrace]:"+line);
                    collector.emit(new Values(line));
                }
                FileUtils.moveFile(f,new File(f.getPath()+System.currentTimeMillis()+FINISH_FILE_SUFFIX));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sensitive"));
    }
}
