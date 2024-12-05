import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ColumnSumMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    private int columnIndex;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        columnIndex = Integer.parseInt(context.getConfiguration().get("columnIndex"));
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");
        if (fields.length > columnIndex) {
            try {
                double columnValue = Double.parseDouble(fields[columnIndex]);
                context.write(new Text("sum"), new DoubleWritable(columnValue));
            } catch (NumberFormatException e) {
                // Handle the case where the column value is not a valid double
            }
        }
    }
}