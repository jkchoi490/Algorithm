package Implementation;

import java.util.*;
import java.io.*;

// Kattis - Concentration
public class Concentration {
    static int n;
    static int[] anthonyOrder;
    static int[] matthewOrder;
    static int[] cardValues;
    static boolean[] revealed;
    static Map<Integer, List<Integer>> knownPositions;
    static int anthonyScore = 0;
    static int matthewScore = 0;
    static int anthonyIndex = 0;
    static int matthewIndex = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        int totalCards = 2 * n;

        anthonyOrder = new int[totalCards];
        matthewOrder = new int[totalCards];
        cardValues = new int[totalCards];
        revealed = new boolean[totalCards];
        knownPositions = new HashMap<>();


        for (int i = 0; i < totalCards; i++) {
            cardValues[i] = i;
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < totalCards; i++) {
            anthonyOrder[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < totalCards; i++) {
            matthewOrder[i] = Integer.parseInt(st.nextToken());
        }

        int currentPlayer = 0;

        while (hasCardsLeft()) {
            boolean continuePlay = playTurn(currentPlayer);
            if (!continuePlay) {
                currentPlayer = 1 - currentPlayer;
            }
        }

        if (anthonyScore > matthewScore) {
            System.out.println(0);
        } else if (matthewScore > anthonyScore) {
            System.out.println(1);
        } else {
            System.out.println(-1);
        }
    }

    static boolean hasCardsLeft() {
        for (boolean r : revealed) {
            if (!r) return true;
        }
        return false;
    }

    static boolean playTurn(int player) {

        int pos1 = -1, pos2 = -1;

        for (Map.Entry<Integer, List<Integer>> entry : knownPositions.entrySet()) {
            int cardValue = entry.getKey();
            List<Integer> positions = entry.getValue();
            int similarValue = getSimilarCard(cardValue);

            if (knownPositions.containsKey(similarValue)) {
                List<Integer> similarPositions = knownPositions.get(similarValue);
                for (int p1 : positions) {
                    if (revealed[p1]) continue;
                    for (int p2 : similarPositions) {
                        if (revealed[p2]) continue;
                        pos1 = p1;
                        pos2 = p2;
                        break;
                    }
                    if (pos1 != -1) break;
                }
            }
            if (pos1 != -1) break;
        }

        if (pos1 != -1 && pos2 != -1) {
            revealed[pos1] = true;
            revealed[pos2] = true;
            if (player == 0) {
                anthonyScore += 2;
            } else {
                matthewScore += 2;
            }
            return true;
        }

        int[] order = (player == 0) ? anthonyOrder : matthewOrder;
        int[] index = (player == 0) ? new int[]{anthonyIndex} : new int[]{matthewIndex};

        while (index[0] < order.length && revealed[order[index[0]]]) {
            index[0]++;
        }
        if (index[0] >= order.length) return false;

        int firstPos = order[index[0]++];
        int firstCard = cardValues[firstPos];
        revealed[firstPos] = true;
        knownPositions.computeIfAbsent(firstCard, k -> new ArrayList<>()).add(firstPos);

        int similarCard = getSimilarCard(firstCard);
        int secondPos = -1;

        if (knownPositions.containsKey(similarCard)) {
            for (int pos : knownPositions.get(similarCard)) {
                if (!revealed[pos]) {
                    secondPos = pos;
                    break;
                }
            }
        }

        if (secondPos == -1) {

            while (index[0] < order.length && revealed[order[index[0]]]) {
                index[0]++;
            }
            if (index[0] >= order.length) {
                if (player == 0) anthonyIndex = index[0];
                else matthewIndex = index[0];
                return false;
            }
            secondPos = order[index[0]++];
        }

        int secondCard = cardValues[secondPos];
        revealed[secondPos] = true;
        knownPositions.computeIfAbsent(secondCard, k -> new ArrayList<>()).add(secondPos);

        if (player == 0) {
            anthonyIndex = index[0];
        } else {
            matthewIndex = index[0];
        }

        if (Math.abs(firstCard - secondCard) == n) {
            if (player == 0) {
                anthonyScore += 2;
            } else {
                matthewScore += 2;
            }
            return true;
        }

        return false;
    }

    static int getSimilarCard(int card) {
        if (card < n) {
            return card + n;
        } else {
            return card - n;
        }
    }
}