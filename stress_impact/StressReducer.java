import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class StressReducer extends Reducer<DoubleWritable, Text, DoubleWritable, Text> {
    public void reduce(DoubleWritable key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        int count = 0;
        double totalSleepQuality = 0;
        double totalDietQuality = 0;

        for (Text val : values) {
            String[] qualities = val.toString().split(",");
            totalSleepQuality += Double.parseDouble(qualities[0]);
            totalDietQuality += Double.parseDouble(qualities[1]);
            count++;
        }

        // Calculate averages
        double avgSleepQuality = totalSleepQuality / count;
        double avgDietQuality = totalDietQuality / count;

        // Emit stress level and corresponding averages
        context.write(key, new Text(avgSleepQuality + "," + avgDietQuality));
    }
}
