package com.edsys.app.user.form;

public class ComposeMail {
	
  private int messageId;
  
  private String fromMailId;
	 
  private String userDMailId;
  
  private String dSubject;
  
  private String messageBody;
  
  private String readStatus;
  
  
public String getReadStatus() {
	return readStatus;
}

public void setReadStatus(String readStatus) {
	this.readStatus = readStatus;
}

public int getMessageId() {
	return messageId;
}

public void setMessageId(int messageId) {
	this.messageId = messageId;
}

public String getFromMailId() {
	return fromMailId;
}

public void setFromMailId(String fromMailId) {
	this.fromMailId = fromMailId;
}

public String getUserDMailId() {
	return userDMailId;
}

public void setUserDMailId(String userDMailId) {
	this.userDMailId = userDMailId;
}

public String getdSubject() {
	return dSubject;
}

public void setdSubject(String dSubject) {
	this.dSubject = dSubject;
}

public String getMessageBody() {
	return messageBody;
}

public void setMessageBody(String messageBody) {
	this.messageBody = messageBody;
}
  
  
	
}
