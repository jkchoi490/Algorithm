package Implementation;

/**
 * Standalone Algorithm (독립형 알고리즘)
 *
 * 특정 소프트웨어 패키지, 외부 라이브러리, 또는 복잡한 프레임워크에 의존하지 않고 그 자체만으로 독립적으로 완전하게 동작하는 알고리즘 입니다.
 * StandaloneAlgorithm을 구현한 내용의 Result는 다른 시스템과 연결되지 않고 독립적으로 해석 가능한 Standalone Result 입니다.
 * StandaloneAlgorithm의 주요 특징은 독립성(Self-containment)으로 특정 OS나 플랫폼, 라이브러리에 의존하지 않고 동작합니다.
 * value는 알고리즘 실행의 최종 결과 값, success는 알고리즘 실행이 논리적으로 성공했는지를 나타내는 플래그 값이고
 * errorType은 잘못된 데이터나 에러를 고치기 위한 필드이며 errorCode는 에러를 식별하고 고치기 위한 정수형 코드 입니다.
 * message는 알고리즘 실행 결과에 대한 설명 메시지이고 operationName은 수행된 알고리즘 연산의 이름, executionTimeMs는 알고리즘 실행에 소요된 시간을 의미합니다.
 * Result 객체 하나의 정보를 통해 알고리즘의 실행 결과와 맥락을 이해할 수 있고, 외부 시스템에 의존하지 않고 독립적으로 동작하는 Standalone 알고리즘을 구현하였습니다.
 */
public class StandaloneAlgorithm {

    /**
     * 완전히 독립적으로 동작하는 알고리즘 클래스이며 외부 라이브러리 의존성 없이 단일 파일로 실행 가능
     */

    public static final class Result<T> {
        private final T value;               // 결과 값
        private final boolean success;       // 성공 메시지
        private final String errorType;      // 에러 정보 타입
        private final String message;        // 메시지
        private final int errorCode;         // 에러 정보 코드
        private final String operationName;  // 수행된 작업 이름
        private final long executionTimeMs;  // 수행 시간


        private Result(T value, boolean success, String errorType, String message,
                       int errorCode, String operationName, long executionTimeMs) {
            this.value = value;
            this.success = success;
            this.errorType = errorType;
            this.message = message;
            this.errorCode = errorCode;
            this.operationName = operationName;
            this.executionTimeMs = executionTimeMs;

        }

        // 성공 결과 생성 - 기본
        public static <T> Result<T> success(T value) {
            return new Result<>(value, true, null, "Success", 0, "unknown",0);
        }

        // 성공 결과 생성 - 상세 정보 포함
        public static <T> Result<T> success(T value, String operationName, long executionTimeMs) {
            return new Result<>(value, true, null, "Success", 0, operationName, executionTimeMs);
        }


        // Getter 메서드들
        public T getValue() { return value; }
        public boolean isSuccess() { return success; }
        public String getErrorType() { return errorType; }
        public String getMessage() { return message; }
        public int getErrorCode() { return errorCode; }
        public String getOperationName() { return operationName; }
        public long getExecutionTimeMs() { return executionTimeMs; }


        @Override
        public String toString() {
            return String.format(
                    "Result{value=%s, success=%s, errorType=%s, message='%s', errorCode=%d, operation='%s', executionTimeMs=%d}",
                    value,
                    success,
                    errorType,
                    message,
                    errorCode,
                    operationName,
                    executionTimeMs
            );
        }


        // 상세 정보 출력
        public String toDetailedString() {
            StringBuilder sb = new StringBuilder();

            sb.append("=== Result Details ===\n");
            sb.append("Value: ")
                    .append(value != null ? value : "null")
                    .append("\n");
            sb.append("Success: ")
                    .append(success)
                    .append("\n");
            sb.append("Error Type: ")
                    .append(errorType != null ? errorType : "null")
                    .append("\n");
            sb.append("Message: ")
                    .append(message)
                    .append("\n");
            sb.append("Error Code: ")
                    .append(errorCode)
                    .append("\n");
            sb.append("Operation: ")
                    .append(operationName)
                    .append("\n");
            sb.append("Execution Time: ")
                    .append(executionTimeMs)
                    .append("ms\n");

            return sb.toString();
        }

    }

    /**
     * 함수형 인터페이스 정의
     */

    //입력을 받아 타입을 반환
    @FunctionalInterface
    public interface Function<T, R> {
        R apply(T t);
    }

    // 조건을 확인
    @FunctionalInterface
    public interface Predicate<T> {
        boolean test(T t);
    }

    //에러를 처리
    @FunctionalInterface
    public interface ErrorHandler<T> {
        Result<T> handle(Result<T> result);
    }

    // 정규화, 스케일링 등 같은 타입 변환
    @FunctionalInterface
    public interface UnaryOperator<T> {
        T apply(T t);
    }

    //에러로 분류
    @FunctionalInterface
    public interface ErrorClassifier {
        String classify(Result<?> result);
    }

    //Result로 변환하는 매퍼
    @FunctionalInterface
    public interface ResultMapper<T, R> {
        Result<R> map(Result<T> result);
    }

    // 초기값 생성, 기본 설정 제공
    @FunctionalInterface
    public interface Supplier<T> {
        T get();
    }


    // ========== 메인 메서드 (테스트) ==========

    public static void main(String[] args) {

        // 데모 테스트 (사용 예시)
        int[] data = {};

        Result<int[]> result;

        boolean check = true;
        for (int v : data) {
            if(v > 0){
                check = true;
            }

            if (v < 0) {
                check = false;
                break;
            }
        }


        result = Result.success(data, "알고리즘 데모 테스트", 0);


        System.out.println(result.toDetailedString());
    }

}