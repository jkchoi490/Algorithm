package Implementation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Independent Axiom (독립 공리)
 *
 * Independent Axiom (독립 공리)란 다른 공리들로 증명되지 않는 공리를 의미합니다.
 * 특정 공리가 다른 공리들로부터 논리적으로 증명될 수 없고 그 공리는 독립적입니다.
 * 즉, a는 다른 공리나 데이터에 의해 만들어지거나 강요되지 않고, 그 자체로 혼자 남아 있는 명제입니다.
 * a는 어떤 규칙의 전제나 결론에도 포함되지 않으며, 전체 체계와 완전히 분리되어 존재합니다.
 * - INDEPENDENT (독립)
 * a는 다른 정보 없이도 혼자 남아 있으며, 나머지 데이터가 a에 영향을 미치지 못합니다.
 * 즉, a는 다른 정보에 의존하지 않고 독립적으로 남습니다.
 * - STANDALONE
 *  독립적임을 만족하면서, 규칙(rule) 어디에도 a가 등장하지 않습니다.
 *  즉, a는 규칙/데이터 흐름에 섞이지 않는 완전히 관련 없는 상태입니다.
 *
 *
 * */

public class IndependentAxiom {

    /* -------------------- Literal -------------------- */
    /**
     * Literal(리터럴)
     *
     * 독립적으로 존재할 수 있는 명제의 최소 단위를 표현합니다.
     *  atom   : 명제의 이름(예: P, INDEPENDENT) → 혼자로 식별되는 고유한 이름
     *
     * 이 코드에서 독립성 판정은 Literal을 중심으로 이루어집니다.
     *
     */
    public static final class Literal {
        private final String atom;      // 명제 이름
        private final boolean checked;

        /**
         * Literal 생성자
         *
         */
        private Literal(String atom, boolean checked) {
            if (atom == null || atom.isBlank()) throw new IllegalArgumentException("atom is blank");
            this.atom = atom.trim();
            this.checked = checked;
        }

        /**
         * 문자열을 Literal로 변환
         * 즉, 문자열 하나로 독립적으로 존재하는 명제 단위를 만들어냅니다.
         */
        public static Literal of(String raw) {
            String s = raw.trim();
            if (s.startsWith("~") || s.startsWith("!")) {
                return new Literal(s.substring(1).trim(), true);
            }
            return new Literal(s, false);
        }

        public Literal check() { return new Literal(atom, !checked); }

        /** atom 이름 반환 */
        public String atom() { return atom; }

        public boolean isChecked() { return checked; }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Literal)) return false;
            Literal other = (Literal) o;
            return checked == other.checked && atom.equals(other.atom);
        }

        @Override public int hashCode() { return Objects.hash(atom, checked); }

        @Override public String toString() { return (checked ? "~" : "") + atom; }
    }

    /* -------------------- Rule -------------------- */
    /**
     *  Rule(규칙)
     * - 어떤 리터럴이 규칙에 등장하지 않으므로 다른 데이터 흐름과 관련이 없다는 뜻을 가집니다.
     * - 독립 공리는 규칙의 흐름과 무관하게 독립적(관련없음)입니다.
     */
    public static final class Rule {
        private final List<Literal> premises;
        private final Literal conclusion;

        public Rule(List<Literal> premises, Literal conclusion) {
            this.premises = List.copyOf(premises);
            this.conclusion = Objects.requireNonNull(conclusion);
        }

        public List<Literal> premises() { return premises; }
        public Literal conclusion() { return conclusion; }

        public static Rule of(List<String> premiseLits, String conclusionLit) {
            List<Literal> ps = premiseLits.stream().map(Literal::of).collect(Collectors.toList());
            return new Rule(ps, Literal.of(conclusionLit));
        }

        @Override public String toString() {
            String left = premises.isEmpty() ? ""
                    : premises.stream().map(Object::toString).collect(Collectors.joining(" & "));
            return left + " -> " + conclusion;
        }
    }

    /* -------------------- Independent Atom Index -------------------- */
    /**
     * Independent Atom Index
     *
     * 어떤 atom이 규칙에 등장하지 않으므로 그 atom은 규칙과 무관하게 혼자 남습니다.
     */
    public static final class IndependentAtomIndex {

        private final Set<String> atomsInRules = new HashSet<>();

        public IndependentAtomIndex(List<Rule> rules) {
            for (Rule r : rules) {
                for (Literal p : r.premises()) atomsInRules.add(p.atom());
                atomsInRules.add(r.conclusion().atom());
            }
        }

        public boolean isIndependentAtomIndex(String atom) {
            return !atomsInRules.contains(atom);
        }
    }

    public static final class InferenceEngine {
        public Set<Literal> closure(Set<Literal> set, List<Rule> rules) {
            Set<Literal> closureSet = new LinkedHashSet<>(set);

            boolean check;
            do {
                check = false;
                for (Rule r : rules) {

                    if (closureSet.contains(r.conclusion())) continue;

                    boolean ok = true;
                    for (Literal p : r.premises()) {
                        if (!closureSet.contains(p)) { ok = false; break; }
                    }

                    if (ok) {
                        closureSet.add(r.conclusion());
                        check = true;
                    }
                }
            } while (check);

            return closureSet;
        }
    }

    /* -------------------- Alone / Standalone / Independent Result -------------------- */

    // IndependentKind
    public enum IndependentKind {
        ALONE,          // 논리적으로 독립임을 의미
        STANDALONE,   // INDEPENDENT + 규칙에 등장하지 않음 -> 완전 혼자/관련 없음
        INDEPENDENT     // 데이터나 규칙에 의존하지 않고 독립적임을 의미
    }

    /**
     *  Analysis
     * - 검사 대상 a가 독립적이라는 결과를 담습니다.
     */
    public static final class Analysis {
        public final Literal axiom;
        public final IndependentKind kind;

        // 규칙에 등장하지 않으므로 독립적임을 표현할 수 있습니다.
        public final boolean checkedInRules;

        public final Set<Literal> literalSet;

        //Analysis 객체 (파라미터, 개수 등은 ChatGPT 참조)
        public Analysis(Literal axiom, IndependentKind kind, boolean checkedInRules, Set<Literal> literalSet) {
            this.axiom = axiom;
            this.kind = kind;
            this.checkedInRules = checkedInRules;
            this.literalSet = literalSet;
        }

        public String narrative() {
            return switch (kind) {
                case ALONE ->
                        " [" + axiom + "] 는 다른 공리 집합으로부터도, 규칙 체계로부터도 " +
                                "어떠한 논리적 영향도 받지 않는 완전한 단독 공리입니다. ";
                case STANDALONE ->
                        " [" + axiom + "] 는 다른 데이터/규칙과 관련 없는 완전 혼자인 공리입니다. " +
                                "(규칙에 언급되지 않음)";
                case INDEPENDENT ->
                        " [" + axiom + "] 는 '독립(Independent)' 공리입니다. " +
                                "(나머지 데이터나/규칙으로 결정되지 않음)";
            };
        }
    }

    /* -------------------- Analyze -------------------- */

    private final InferenceEngine engine = new InferenceEngine();

    /**
     * analyzeIndependent
     * 분석 후 독립적임이라는 결과를 반환합니다.
     */
    public Optional<Analysis> analyzeIndependent(Set<Literal> literalSet, List<Rule> rules, Literal axiomToTest) {
        IndependentAtomIndex standalone = new IndependentAtomIndex(rules);

        Set<Literal> set = new LinkedHashSet<>(literalSet);
        set.remove(axiomToTest);

        Set<Literal> derived = engine.closure(set, rules);

        boolean canDerive = derived.contains(axiomToTest);
        boolean canDeriveNeg = derived.contains(axiomToTest.check());

        if (canDerive || canDeriveNeg) {
            return Optional.empty();
        }

        // 완전 혼자(관련 없음)임을 표현
        boolean checkedInRules = standalone.isIndependentAtomIndex(axiomToTest.atom());
        IndependentKind kind = checkedInRules ? IndependentKind.INDEPENDENT : IndependentKind.STANDALONE;

        return Optional.of(new Analysis(axiomToTest, kind, checkedInRules, derived));
    }

    /* -------------------- Demo -------------------- */

    public static void main(String[] args) {
        IndependentAxiom analyzer = new IndependentAxiom();

        // 공리/사실 집합 A 예시
        Set<Literal> facts = new LinkedHashSet<>(Arrays.asList(
                Literal.of("STANDALONE"),
                Literal.of("ALONE"),
                Literal.of("INDEPENDENT")
        ));

        // 규칙 집합 R 예시
        List<Rule> rules = List.of(
                Rule.of(List.of("P", "Q"), "R"),
                Rule.of(List.of("R"), "S"),
                Rule.of(List.of("Q"), "~T")
        );

        // 데모
        analyzer.analyzeIndependent(facts, rules, Literal.of("STANDALONE"))
                .ifPresentOrElse(
                        a -> System.out.println(a.narrative()),
                        () -> System.out.println("")
                );

        analyzer.analyzeIndependent(facts, rules, Literal.of("ALONE"))
                .ifPresentOrElse(
                        a -> System.out.println(a.narrative()),
                        () -> System.out.println("")
                );

        analyzer.analyzeIndependent(facts, rules, Literal.of("INDEPENDENT"))
                .ifPresentOrElse(
                        a -> System.out.println(a.narrative()),
                        () -> System.out.println("")
                );

    }
}
