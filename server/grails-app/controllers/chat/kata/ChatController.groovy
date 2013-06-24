package chat.kata

class ChatController {

	ChatService chatService
	

	def list(Integer seq) {
		
		if(hasErrors()){
			log.error("Invalid seq: ${errors.getFieldError('seq').rejectedValue}")
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

	def send(ChatMessage msg){
		chatService.putChatMessage(new ChatMessage(request.JSON))
		render(status:201)
	}
}
