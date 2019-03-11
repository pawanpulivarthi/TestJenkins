package com.edsys.app.taglib;

public class TextTag
  extends org.apache.struts.taglib.html.TextTag
{
  private static final long serialVersionUID = 4406767066637071336L;
  protected String autocomplete;
  
  public String getAutocomplete()
  {
    return this.autocomplete;
  }
  
  public void setAutocomplete(String autocomplete)
  {
    this.autocomplete = autocomplete;
  }
  
  public void prepareOtherAttributes(StringBuffer sb)
  {
    super.prepareOtherAttributes(sb);
    if (this.autocomplete != null) {
      prepareAttribute(sb, "autocomplete", this.autocomplete);
    }
  }
}
