package Implementation;

// LeetCode - Average Waiting Time
public class AverageWaitingTime {

    // 문제 정의 및 해결을 위한 WaitingTime을 계산합니다.
    // order가 들어오면 chef는 요리를 시작하고 answer를 return 합니다.
    public double averageWaitingTime(int[][] customers) {
        long totalWaitingTime = 0;
        int currentTime = 0;

        for (int[] customer : customers) {
            int arrival = customer[0];
            int prepTime = customer[1];

            int startTime = Math.max(currentTime, arrival);

            // Chef finishes = startTime + prepTime
            int finishTime = startTime + prepTime;

            // Waiting time = finishTime - arrival
            int waitingTime = finishTime - arrival;

            totalWaitingTime += waitingTime;
            currentTime = finishTime;
        }

        return (double) totalWaitingTime / customers.length;
    }
}
