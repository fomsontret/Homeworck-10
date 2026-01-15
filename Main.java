import java.util.Scanner;

class PasswordChecker {
    private Integer minLength = null;
    private Integer maxRepeats = null;

    public void setMinLength(int minLength) {
        if (minLength < 0) {
            throw new IllegalArgumentException("Минимальная длина не может быть отрицательной");
        }
        this.minLength = minLength;
    }

    public void setMaxRepeats(int maxRepeats) {
        if (maxRepeats <= 0) {
            throw new IllegalArgumentException("Максимальное количество повторений должно быть больше 0");
        }
        this.maxRepeats = maxRepeats;
    }

    public boolean verify(String password) {
        // Проверяем, что настройки были установлены
        if (minLength == null || maxRepeats == null) {
            throw new IllegalStateException("Сначала нужно установить настройки чекера");
        }

        // Проверка минимальной длины
        if (password.length() < minLength) {
            return false;
        }

        // Проверка максимального количества повторений символов подряд
        int currentRepeats = 1;
        for (int i = 1; i < password.length(); i++) {
            if (password.charAt(i) == password.charAt(i - 1)) {
                currentRepeats++;
                if (currentRepeats > maxRepeats) {
                    return false;
                }
            } else {
                currentRepeats = 1;
            }
        }

        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Ввод настроек
            System.out.print("Введите мин. длину пароля: ");
            int minLength = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите макс. допустимое количество повторений символа подряд: ");
            int maxRepeats = Integer.parseInt(scanner.nextLine());

            // Создание и настройка чекера
            PasswordChecker checker = new PasswordChecker();
            checker.setMinLength(minLength);
            checker.setMaxRepeats(maxRepeats);

            // Бесконечный цикл проверки паролей
            while (true) {
                System.out.print("Введите пароль или end: ");
                String password = scanner.nextLine();

                if ("end".equalsIgnoreCase(password)) {
                    break;
                }

                try {
                    boolean isValid = checker.verify(password);
                    if (isValid) {
                        System.out.println("Подходит!");
                    } else {
                        System.out.println("Не подходит!");
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Программа завершена");

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в настройках: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}