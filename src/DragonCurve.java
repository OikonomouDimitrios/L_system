import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DragonCurve{
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        String startingSequence = "FX";
        try
        {
            for(int i=0; i<8; i++)
            {
                Future<String> future = service.submit(new calculateGen(8));
                String finalSequence = future.get();
                startingSequence = finalSequence;

                System.out.println(startingSequence);
            }
            service.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}