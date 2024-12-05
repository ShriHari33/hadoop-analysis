import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CocurricularReducer extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<String[]> records = new ArrayList<>();

        // Collect all records for the same key
        for (Text value : values) {
            records.add(value.toString().split(","));
        }

        // Merge records (simplified logic)
        StringBuilder mergedRecord = new StringBuilder();
        for (String[] record : records) {
            for (String field : record) {
                if (!field.trim().isEmpty()) {
                    mergedRecord.append(field.trim()).append(",");
                } else {
                    mergedRecord.append("-1111,"); // Fill missing values
                }
            }
        }

        context.write(key, new Text(mergedRecord.toString()));
    }
}
