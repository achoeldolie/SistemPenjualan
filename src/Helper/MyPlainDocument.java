
package Helper;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MyPlainDocument extends PlainDocument {
  public static final int TYPE_TEXT = 1;
  public static final int TYPE_DOUBLE = 2;
  public static final int TYPE_INT = 3;
  
  private int limit =0 ;
  private int typeInput;
    
  public MyPlainDocument(int typeInput) {
    this.typeInput = typeInput;
  }
  public MyPlainDocument(int typeInput,int limit) {
    this.limit = limit;
    this.typeInput = typeInput;
  }
 
  @Override
  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
      if (str == null)
          return;
      
      String sampleText = getText(0, getLength()) + str;
      try{          
            if(typeInput == TYPE_DOUBLE && !sampleText.isEmpty()){
                Double.parseDouble(sampleText);
            }
            else if(typeInput == TYPE_INT && !sampleText.isEmpty()){
                Integer.parseInt(sampleText);
            }
            
            if(limit == 0){
                super.insertString(offs, str, a);
            }else if ((getLength() + str.length()) <= limit) {
                super.insertString(offs, str, a);         
            }         
      }catch(Exception ex){}
      
  }
}

