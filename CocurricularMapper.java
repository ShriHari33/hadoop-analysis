import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class CocurricularMapper extends Mapper<LongWritable, Text, Text, Text> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Skip the header line
        if (key.get() == 0 && value.toString().contains("ID"))
            return;

        // Split the input line
        String[] fields = value.toString().split(",");

        // fill missing values with -1
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == null || fields[i].trim().isEmpty() || fields[i].equalsIgnoreCase("NULL")) {
                fields[i] = "-1";
            }
        }

        // Assuming ID is the first column and is the join key
        String recordKey = fields[0].trim();
        context.write(new Text(recordKey), new Text(value));
    }
}
