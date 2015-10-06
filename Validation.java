package validation;

public class Validation {

	public static boolean validateString(String data, String format) {
		// For format
		int base = 0;
		// For data
		int check = 0;
		
		int formatLength = format.length();
		int datalength = data.length();
		
		while (base < formatLength) {
			char current = format.charAt(base);
			while (Character.isLetter(current) || current == '-') {
				switch (current) {
				case 'L':
					if (!Character.isLetter(data.charAt(check)))
						return false;
					break;
				case 'N':
					if (!Character.isDigit(data.charAt(check)))
						return false;
					break;
				case '-':
					if(data.charAt(check) != '-')
						return false;
					break;
				}
				base++;
				check++;
				if (base >= formatLength) {
					if (check >= datalength)
						return true;
					else
						return false;
				} else if (check >= datalength)
					return false;
				else
					current = format.charAt(base);
			}
			
			if (current == '{')
			{
				if(data.charAt(check-1) == ']')
				{
					
				}
			}
			else if (current == '[') {
				String internalFormat = findFormat(base, format);
				int internalFormatLength = internalFormat.length();
				if (!internalFormat.toLowerCase().equals(
						data.substring(check, check + internalFormatLength).toLowerCase()))
					return false;
				check += internalFormatLength;
				base += internalFormatLength + 2;
			}

		}

		return true;
	}

	private static String findFormat(int start, String format) {
		String internalFormat = "";
		for (int add = 1;; add++) {
			char current = format.charAt(add);
			if (format.charAt(start + add) == ']'
					|| format.charAt(start + add) == '}')
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
