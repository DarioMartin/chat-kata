package chat.kata

class ChatController {

	ChatService chatService

	def list(Integer seq) {

		if(hasErrors()){
			log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
			render(status:400, contentType: "text/json") {error = "Invalid seq parameter"}
		}
		else{
			List<ChatMessage> allMessages = new ArrayList()
			Integer aux_seq = chatService.collectChatMessages(allMessages, seq)

			render(contentType: "text/json"){
				messages = []

				for(m in allMessages){
					messages.add(nick:m.getNick(),message:m.getMessage())
				}

				last_seq = aux_seq
			}
		}
	}

	def send(){
		if(!request.JSON){
			render(status:400,contentType: "text/json") {error = "Invalid body"}
		}
		else{
			ChatMessage msg = new ChatMessage(request.JSON)

			if(!msg.validate()){
				if(msg.errors.hasFieldErrors("nick"))
					render(status:400,contentType: "text/json") {error = "Missing nick parameter"}
				if(msg.errors.hasFieldErrors("message"))
					render(status:400,contentType: "text/json") {error = "Missing message parameter"}
			}
			else{
				chatService.putChatMessage(msg)
				render(status:201)
			}
		}
	}
}
