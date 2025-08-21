package com.project.freelancehub.model;

import java.time.LocalDateTime;

public class Requestentry {

	    private String freelancerId;
	    private String status;
	    private LocalDateTime requestedAt;
		public String getFreelancerId() {
			return freelancerId;
		}
		public void setFreelancerId(String freelancerId) {
			this.freelancerId = freelancerId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public LocalDateTime getRequestedAt() {
			return requestedAt;
		}
		public void setRequestedAt(LocalDateTime requestedAt) {
			this.requestedAt = requestedAt;
		}
	    

}
