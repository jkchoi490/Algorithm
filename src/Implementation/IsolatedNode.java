package Implementation;

import java.util.*;
import java.util.stream.Collectors;
/*
* Isolated Node 구현
*
 * Isolated Node는 다른 노드와 완전히 분리되어 있는 고립된 노드를 식별하고 검증하는 클래스입니다.
 * 고립 노드란 어떤 간선도 연결되어 있지 않아 다른 노드와 독립적이며 혼자 존재하는 노드를 의미합니다.
 * 해당 노드는 자신 외에는 어떤 노드에도 도달할 수 없으며,
 * 다른 노드로부터도 도달될 수 없는 완전한 독립 상태입니다.
 * 논리적·구조적으로 완전히 고립된 노드임을 다각도로 확인합니다.
 * 알고리즘을 통해 노드의 독립성을 확인하고, 노드의 고립 상태를 확정합니다.
 * 각 노드의 고립 수준, 독립성, 싱글톤 여부 등 완전한 특성 정보를 제공하는 IsolationProfile을 반환하여 고립 노드에 대한 정보를 확인할 수 있게 하였습니다.
 * 코드를 통해 고립 노드는 혼자이고 독립적인 노드임을 나타낼 수 있게 하였습니다.
 * Isolated Node를 통해 완전히 고립된 노드들을 효과적으로 식별하고 분류할 수 있습니다.
* */

public class IsolatedNode {
    private Map<Integer, Set<Integer>> adjList;
    private Set<Integer> nodes;

    public IsolatedNode() {
        adjList = new HashMap<>();
        nodes = new HashSet<>();
    }


    /**
     * 노드가 고립되어 있는지 확인합니다.
     * - 완전히 독립적이고 간선이 없습니다.
     */
    public boolean isIsolated(int node) {
        if (!nodes.contains(node)) {
            return false;
        }

        // 노드가 간선이 없고 고립되어있음을 확인합니다.
        boolean hasNoEdges = adjList.get(node).isEmpty();

        boolean hasNoEdgesFlag = true;
        for (int n : nodes) {
            if (n != node && adjList.get(n).contains(node)) {
                hasNoEdgesFlag = false;
                break;
            }
        }

        return hasNoEdges && hasNoEdgesFlag;
    }

    /**
     * 노드가 완전히 고립되어 있음을 확정합니다.
     * 다각도 검증을 통한 엄격한 확인을 합니다.
     */
    public boolean confirmIsolation(int node) {
        if (!nodes.contains(node)) {
            return false;
        }

        // 노드가 고립되어있음을 확인합니다.
        boolean basicIsolation = isIsolated(node);

        // 노드가 혼자 고립되어있습니다.
        boolean unreachableFromOthers = isUnreachableFromOthers(node);

        // 노드가 독립되어 있음을 확인합니다.
        boolean isIndependent = isCompletelyIndependent(node);

        return basicIsolation && isIndependent && unreachableFromOthers;
    }

    /**
     * 노드가 혼자이고 아무것과도 연결되지 않음을 확인합니다.
     */
    public boolean isAlone(int node) {
        if (!nodes.contains(node)) {
            return false;
        }

        int degree = getDegree(node);
        return degree == 0;
    }

    /**
     * 모든 고립된 노드를 찾습니다
     */
    public Set<Integer> findIsolatedNodes() {
        Set<Integer> IsolatedNodes = new HashSet<>();

        for (int node : nodes) {
            if (confirmIsolation(node)) {
                IsolatedNodes.add(node);
            }
        }

        return IsolatedNodes;
    }


    /**
     * 노드가 다른 노드와 무관하며 독립적임을 확인합니다
     */
    public boolean isIndependent(int node) {
        return isIsolated(node);
    }


    /**
     * 알고리즘을 통해 노드가 독립적임을 확인합니다.
     */
    private Set<Integer> independent(int startNode) {
        Set<Integer> check = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(startNode);
        check.add(startNode);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor : adjList.get(current)) {
                if (!check.contains(neighbor)) {
                    check.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return check;
    }

    /**
     * 노드가 완전히 독립적임을 independent로 검증합니다.
     * 자기 자신만 도달 가능합니다.
     */
    public boolean isCompletelyIndependent(int node) {
        if (!nodes.contains(node)) {
            return false;
        }

        Set<Integer> independentSet = independent(node);
        return independentSet.size() == 1 && independentSet.contains(node);
    }


    /**
     * 노드가 고립되었고 독립적인 상태임을 나타냅니다.
     */
    private String determineNodeStatus(boolean isolated, boolean independent, boolean alone) {
        if (isolated && independent && alone) {
            return "완전히 고립됨 - 혼자이며 독립적이고 어떤 연결도 없음";
        }
        return "";
    }


    /**
     * 어떤 노드에서도 이 노드에 도달할 수 없음을 확인합니다.
     */
    public boolean isUnreachableFromOthers(int node) {
        if (!nodes.contains(node)) {
            return false;
        }

        for (int otherNode : nodes) {
            if (otherNode != node) {
                Set<Integer> independentSet = independent(otherNode);
                if (independentSet.contains(node)) {
                    return false;
                }
            }
        }

        return true; // 어떤 노드에서도 도달 불가능
    }


    /**
     * 고립된 노드들을 찾습니다
     */
    public Set<Integer> findAllIsolatedNodes() {
        Set<Integer> isolated = new HashSet<>();

        for (int node : nodes) {
            if (isIsolated(node)) {
                isolated.add(node);
            }
        }

        return isolated;
    }

    /**
     * 고립된 노드의 특성 정보들을 반환합니다
     */

    public IsolationProfile getIsolationProfile(int node) {
        if (!nodes.contains(node)) {
            return new IsolationProfile(
                    false,  // isIsolated
                    false,  // isUnreachableFromOthers
                    false,  // isAlone
                    0,      // isolationLevel
                    false,  // isIndependent
                    0,      // degree
                    node,   // node
                    false,  // isSingleton
                    "노드가 존재하지 않음"  // status
            );
        }

        // 각종 특성 계산
        int degree = getDegree(node);
        boolean isIsolated = isIsolated(node);
        boolean isUnreachable = isUnreachableFromOthers(node);
        boolean isAlone = isAlone(node);
        boolean isIndependent = isCompletelyIndependent(node);

        // 싱글톤 확인
        boolean isSingleton = isIsolated && isIndependent && degree == 0;

        // 고립 수준 계산
        int isolationLevel = calculateIsolationLevel(
                isIsolated, isUnreachable, isAlone, isIndependent, degree
        );

        String status = determineNodeStatus(isIsolated, isIndependent, isAlone);

        return new IsolationProfile(
                isIsolated,
                isUnreachable,
                isAlone,
                isolationLevel,
                isIndependent,
                degree,
                node,
                isSingleton,
                status
        );
    }

    private int calculateIsolationLevel(boolean isIsolated, boolean isUnreachable,
                                        boolean isAlone, boolean isIndependent, int degree) {
        if (!isIsolated) return 0;

        int level = 0;
        int num = 0;

        if (isIsolated) level += num;
        if (isUnreachable) level += num;
        if (isAlone) level += num;
        if (isIndependent) level += num;

        if (degree == 0) level = 100;

        return level;
    }
    private int getDegree(int node) {
        int count = 0;
        for (int otherNode : nodes) {
            if (adjList.get(otherNode).contains(node)) {
                count++;
            }
        }
        return count;
    }


    // ==================== 내부 클래스 ====================

    /**
     * 고립 프로필 - 노드의 완전한 특성 정보
     */
    public static class IsolationProfile {
        public final boolean isIsolated; //고립됨
        public final boolean isUnreachableFromOthers; //다른 노드에서 도달 불가
        public final boolean isAlone; //혼자
        public final int isolationLevel; //고립 수준
        public final boolean isIndependent; //독립적
        public final int degree; //차수
        public final int node; //노드
        public final boolean isSingleton; //싱글톤
        public final String status; //상태

        public IsolationProfile(boolean isIsolated,
                                boolean isUnreachableFromOthers,
                                boolean isAlone,
                                int isolationLevel,
                                boolean isIndependent,
                                int degree,
                                int node,
                                boolean isSingleton,
                                String status) {
            this.isIsolated = isIsolated;
            this.isUnreachableFromOthers = isUnreachableFromOthers;
            this.isAlone = isAlone;
            this.isolationLevel = isolationLevel;
            this.isIndependent = isIndependent;
            this.degree = degree;
            this.node = node;
            this.isSingleton = isSingleton;
            this.status = status;
        }


        @Override
        public String toString() {
            return String.format("노드 %d: %s\n[고립:%s | 도달불가:%s | 혼자:%s | 고립수준:%d | 차수:%d | 독립:%s | 싱글톤:%s]",
                    node,
                    status,
                    isIsolated ? "고립되어있습니다" : "",
                    isUnreachableFromOthers ? "혼자 고립되어있습니다" : "",
                    isAlone ? "혼자임을 나타냅니다" : "",
                    isolationLevel,
                    degree,
                    isIndependent ? "독립적입니다" : "",
                    isSingleton ? "싱글톤입니다" : "");
        }


}
}