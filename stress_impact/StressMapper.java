import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class StressMapper extends Mapper<LongWritable, Text, DoubleWritable, Text> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");
        try {
            // Extract relevant fields (skip header by checking for "academic_stress_level")
            if (!fields[3].equalsIgnoreCase("academic_stress_level")) {
                double academicStressLevel = Double.parseDouble(fields[3]);
                double sleepQuality = Double.parseDouble(fields[6]);
                double dietQuality = Double.parseDouble(fields[7]);

                // Emit stress level as key, and sleep and diet quality as a concatenated string
                context.write(new DoubleWritable(Math.round(academicStressLevel * 10.0) / 10.0),
                        new Text(sleepQuality + "," + dietQuality));
            }
        } catch (Exception e) {
            // Handle invalid data
        }
    }
}
