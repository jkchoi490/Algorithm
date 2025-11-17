package Greedy;

import java.io.*;
import java.util.*;

//Sphere Online Judge - Helper
public class Helper {

    static class Interval {
        int start, end;
        Interval(int s, int e) { start=s; end=e; }
    }

    static class Job {
        int idx;        // 학생 index
        int duration;   // 해결 시간
        int deadline;   // 마감 시간
        int profit;
        String subject;
        Job(int idx, int duration, int deadline, int profit, String subject) {
            this.idx=idx; this.duration=duration; this.deadline=deadline;
            this.profit=profit; this.subject=subject;
        }
    }

    static class Assigned {
        Job job;
        int start, end;
        Assigned(Job j, int s, int e) {
            job=j; start=s; end=e;
        }
    }

    static int toMin(String s) {
        // "HH:MM"
        int h = Integer.parseInt(s.substring(0,2));
        int m = Integer.parseInt(s.substring(3,5));
        return h*60 + m;
    }

    static int DAY = 24*60; //하루 24시간

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // map subject -> index
        Map<String,Integer> subjIndex = new HashMap<>();
        String[] subjectNames = new String[m];
        for (int i=0;i<m;i++){
            subjectNames[i] = br.readLine().trim();
            subjIndex.put(subjectNames[i], i);
        }

        int[] t = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i=0;i<m;i++) t[i] = Integer.parseInt(st.nextToken());

        String[] forb = new String[4];
        for (int i=0;i<4;i++) forb[i] = br.readLine().trim();


        boolean[] bad = new boolean[DAY];
        for (int i=0;i<4;i++){
            String[] parts = forb[i].split("-");
            int s = toMin(parts[0]);
            int e = toMin(parts[1]);
            for (int x=s; x<=e; x++) bad[x]=true;
        }


        List<Interval> dailyFree = new ArrayList<>();
        int i=0;
        while (i<DAY){
            if (!bad[i]){
                int j=i;
                while (j+1<DAY && !bad[j+1]) j++;
                dailyFree.add(new Interval(i,j));
                i = j+1;
            } else i++;
        }

        List<Interval> freeAll = new ArrayList<>();
        for (int day=0; day<k; day++){
            int base = day*DAY;
            for (Interval in: dailyFree){
                freeAll.add(new Interval(base+in.start, base+in.end));
            }
        }

        List<Job> jobs = new ArrayList<>();
        for (int si=1; si<=n; si++){
            // subject, day, exam time, cost
            st = new StringTokenizer(br.readLine());
            String subject = st.nextToken();
            int examDay = Integer.parseInt(st.nextToken());
            String examTimeStr = st.nextToken();
            int cost = Integer.parseInt(st.nextToken());

            if (!subjIndex.containsKey(subject)) continue;

            int subjIdx = subjIndex.get(subject);
            int duration = t[subjIdx];
            int examMin = toMin(examTimeStr);
            int deadline = (examDay-1)*DAY + examMin - 1;
            if (deadline < 0) continue;

            jobs.add(new Job(si, duration, deadline, cost, subject));
        }

        Collections.sort(jobs, (a,b)->Integer.compare(a.deadline,b.deadline));

        List<Assigned> result = new ArrayList<>();

        for (Job job: jobs){
            int need = job.duration;
            int deadline = job.deadline;

            for (int idx=0; idx<freeAll.size(); idx++){
                Interval in = freeAll.get(idx);
                if (in.start > deadline) break;

                int R = Math.min(in.end, deadline);
                int L = R - need + 1;
                if (L >= in.start){
                    result.add(new Assigned(job, L, L+need-1));

                    int oldStart = in.start, oldEnd = in.end;
                    freeAll.remove(idx);
                    if (oldStart <= L-1)
                        freeAll.add(idx, new Interval(oldStart, L-1));
                    if (L+need <= oldEnd)
                        freeAll.add(idx+ (oldStart<=L-1 ? 1:0),
                                new Interval(L+need, oldEnd));

                    idx--;
                    break;
                }
            }
        }

        long maxProfit = 0;
        for (Assigned a: result) maxProfit += a.job.profit;

        System.out.println(maxProfit);
        System.out.println(result.size());

        for (Assigned a: result){
            int s = a.start;
            int e = a.end;
            int sd = s / DAY + 1;
            int ed = e / DAY + 1;
            int stime = s % DAY;
            int etime = e % DAY;
            System.out.printf("%d %d %02d:%02d %d %02d:%02d\n",
                    a.job.idx,
                    sd,
                    stime/60, stime%60,
                    ed,
                    etime/60, etime%60
            );
        }
    }
}
