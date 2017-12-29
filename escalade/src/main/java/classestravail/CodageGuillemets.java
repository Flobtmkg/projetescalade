package classestravail;

public class CodageGuillemets {
	//
	//
	public static String getTexteEncode(String textInput) {
		String textOutput=null;
		if(textInput!=null && textInput.equals("")==false) {
			textOutput=textInput.replace("'", "&#x27");
		}else{
			textOutput=textInput;
		}
		return textOutput;
	}
	//
	//
	public static String getTexteDecode(String textInput) {
		String textOutput=null;
		if(textInput!=null && textInput.equals("")==false) {
			textOutput=textInput.replace("&#x27","'");
		}else{
			textOutput=textInput;
		}	
		return textOutput;
	}
	//
	//
}
