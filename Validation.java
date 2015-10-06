package validation;

public class Validation {

	private static final int SUCCESS = 38393;

	public static boolean validateString(String data, String format) {
		int formatIndex = quickValidate(data, format);
		if (formatIndex == SUCCESS)
			return true;
		int dataIndex = formatIndex;

		int formatLength = format.length(), dataLength = data.length();
		while (formatIndex < formatLength) {
			char currentFormat = format.charAt(formatIndex);
			if (currentFormat == '[') {
				formatIndex++;
				int lastIndex = findEnd(format, formatIndex);
				if (lastIndex == formatLength - 1) {
					return data.substring(dataIndex).equals(format.substring(formatIndex, lastIndex));
				} else if (format.charAt(lastIndex + 1) == '{') {
					char repeat = format.charAt(lastIndex - 1);
					String internalFormat = findFormat(formatIndex, format);
					System.out.println(internalFormat);
				} else {
					String internalFormat = format.substring(formatIndex, lastIndex);
					int dist = lastIndex - formatIndex;
					if (!internalFormat.equals(data.substring(dataIndex, dataIndex + dist)))
						return false;
					dataIndex += dist;
					formatIndex += dist + 1;
				}
			} else if (currentFormat == '{') {
				char repeat = format.charAt(formatIndex - 1);
				String internalFormat = findFormat(formatIndex + 1, format);
				System.out.println(internalFormat);
				int[] solutions = repeatValidate(data.substring(dataIndex), parseFirst(internalFormat),
						parseLast(internalFormat), repeat);
				print(solutions);
			} else {
				int add = quickValidate(data.substring(dataIndex), format.substring(formatIndex));
				if (add == 0)
					formatIndex++;
				formatIndex += add;
				dataIndex += add;
			}
		}
		return true;
	}

	private static int parseFirst(String format) {
		char current = format.charAt(0);
		int first = 0;
		for (int i = 1; current != '-'; i++) {
			first = first * 10 + Integer.parseInt(current + "");
			current = format.charAt(i);
		}
		return first;
	}

	private static int parseLast(String format) {
		char current = format.charAt(0);
		int last = 0;
		int i = 0;
		for (i = 1; current != '-'; i++) {
			current = format.charAt(i);
		}
		while (i < format.length()) {
			last = last * 10 + Integer.parseInt(format.charAt(i) + "");
			i++;
		}
		return last;
	}

	public static int[] repeatValidate(String data, int min, int max, char repeat) {
		int[] solutions = new int[max];
		int currentIndex = 0;

		if (repeat == 'L') {
			for (int i = 0; i < data.length() && i <= max; i++) {
				if (!Character.isLetter(data.charAt(i))) {
					if (i < min)
						return null;
					else
						return trimArray(solutions);
				} else {
					solutions[currentIndex] = i;
					currentIndex++;
				}

			}
		} else if (repeat == 'N') {
			for (int i = 0; i < data.length() && i <= max; i++) {
				if (!Character.isDigit(data.charAt(i))) {
					if (i < min)
						return null;
					else
						return trimArray(solutions);
				} else {
					solutions[currentIndex] = i;
					currentIndex++;
				}

			}
		} else {
			for (int i = 0; i < data.length() && i <= max; i++) {
				if (data.charAt(i) != repeat) {
					if (i < min)
						return null;
					else
						return trimArray(solutions);
				} else {
					solutions[currentIndex] = i;
					currentIndex++;
				}

			}
		}

		return solutions;
	}

	public static void print(int[] array) {
		for (int i : array)
			System.out.println(i);
	}

	private static int[] trimArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0 && (i == array.length - 1 || array[i + 1] == 0)) {
				int[] newArray = new int[i];
				for (int j = 0; j < i; j++) {
					newArray[j] = array[j];
				}
				return newArray;
			}
		}
		return array;
	}

	private static int quickValidate(String data, String format) {
		char current = 0;
		int min = 0;
		if (data.length() < format.length())
			min = data.length();
		else
			min = format.length();
		for (int i = 0; i < min; i++) {
			current = format.charAt(i);
			switch (current) {
			case 'L':
				if (!Character.isLetter(data.charAt(i)))
					if (i == min - 1 || data.charAt(i + 1) != '{')
						return i;
					else
						return i - 1;
				break;
			case 'N':
				if (!Character.isDigit(data.charAt(i)))
					if (i == min - 1 || data.charAt(i + 1) != '{')
						return i;
					else
						return i - 1;
				break;
			case '-':
				if (data.charAt(i) != '-')
					if (i == min - 1 || data.charAt(i + 1) != '{')
						return i;
					else
						return i - 1;
				break;
			case '{':
				return i - 1;
			default:
				return i;
			}
		}

		return SUCCESS;
	}

	private static int findEnd(String format, int start) {
		int end = 0;
		for (int i = start; i < format.length(); i++) {
			if (format.charAt(i) == ']' || format.charAt(i) == '}')
				return i;
		}
		return end;
	}

	private static String findFormat(int start, String format) {
		String internalFormat = "";
		for (int add = 0;; add++) {
			char current = format.charAt(start + add);
			if (current == ']' || current == '}')
				return internalFormat;
			internalFormat += current;
		}
	}

	public static boolean validateNumber(double number, double min, double max) {
		return number >= min && number <= max;
	}

	public static boolean isInteger(String data) {
		int length = data.length();
		char first = data.charAt(0);
		if (first == '-' || Character.isDigit(first))
			for (int i = 1; i < length; i++) {
				if (!Character.isDigit(data.charAt(i)))
					return false;
			}
		return true;
	}

	public static boolean isString(String data) {
		return true;
	}
}
