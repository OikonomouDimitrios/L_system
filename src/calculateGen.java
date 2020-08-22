import java.util.concurrent.Callable;

public class calculateGen implements Callable<String> {
    private int iterations;

    public calculateGen(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public String call() throws Exception {
        String startingSequence = "FX";
        String nextSequence = "";
        String[] tableofRules = {"X+YF+", "-FX-Y"};
        for (int j = 0; j < iterations; j++) {
            for (int i = 0; i < startingSequence.length(); i++) {
                char currentSequence = startingSequence.charAt(i);
                if (currentSequence == 'X') {
                    nextSequence += tableofRules[0];
                } else if (currentSequence == 'Y') {
                    nextSequence += tableofRules[1];
                } else {
                    nextSequence += currentSequence;
                }

            }
            startingSequence = nextSequence;
        }
        return startingSequence;
    }


}
