package fr.sii.atlantique.siistem.notification.scheduler.model.notification;

public class Notification {

	private boolean hasMail;
	private Mail mail;
	private boolean hasSms;
	private SMS sms;

	public Notification() {
		super();
	}

	public Notification(boolean hasMail, Mail mail, boolean hasSms, SMS sms) {
		super();
		this.hasMail = hasMail;
		this.mail = mail;
		this.hasSms = hasSms;
		this.sms = sms;
	}

	public boolean isHasMail() {
		return hasMail;
	}

	public void setHasMail(boolean hasMail) {
		this.hasMail = hasMail;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public boolean isHasSms() {
		return hasSms;
	}

	public void setHasSms(boolean hasSms) {
		this.hasSms = hasSms;
	}

	public SMS getSms() {
		return sms;
	}

	public void setSms(SMS sms) {
		this.sms = sms;
	}

	@Override
	public String toString() {
		return "Notification [hasMail=" + hasMail + ", mail=" + mail + ", hasSms=" + hasSms + ", sms=" + sms + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (hasMail ? 1231 : 1237);
		result = prime * result + (hasSms ? 1231 : 1237);
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((sms == null) ? 0 : sms.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (hasMail != other.hasMail)
			return false;
		if (hasSms != other.hasSms)
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (sms == null) {
			if (other.sms != null)
				return false;
		} else if (!sms.equals(other.sms))
			return false;
		return true;
	}

}
