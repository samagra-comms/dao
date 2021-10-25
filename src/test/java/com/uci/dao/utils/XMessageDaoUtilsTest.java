package com.uci.dao.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.uci.dao.models.XMessageDAO;

import lombok.SneakyThrows;
import messagerosa.core.model.SenderReceiverInfo;
import messagerosa.core.model.XMessage;
import messagerosa.core.model.XMessagePayload;
import messagerosa.core.model.XMessage.MessageState;
import messagerosa.core.model.XMessage.MessageType;

public class XMessageDaoUtilsTest {
//	@Mock
//	XMessageDAOUtils xMessageDAOUtils;
	
	@SneakyThrows
    @BeforeEach
    public void init() {

    }
	
	@Test
	public void convertXMessageToDAOTest() {
		SenderReceiverInfo from = SenderReceiverInfo.builder().userID("7597185708").build();
		SenderReceiverInfo to = SenderReceiverInfo.builder().userID("admin").build();
		XMessagePayload payload = XMessagePayload.builder().text("Hi UCI").build();
		
		
		XMessage xmsg = XMessage.builder()
				.app("UCI Demo")
				.channelURI("Whatsapp")
				.providerURI("Netcore")
				.messageType(MessageType.TEXT)
				.messageState(MessageState.REPLIED)
				.from(from)
				.to(to)
				.transformers(null)
				.payload(payload)
				.timestamp(Timestamp.valueOf(LocalDateTime.now()).getTime())
				.build();
		
//		XMessage xmsg = new XMessage();
//				xmsg.setApp("UCI Demo");
//				xmsg.setChannelURI("Whatsapp");
//				xmsg.setProviderURI("Netcore");
//				xmsg.setMessageType(MessageType.TEXT);
//				xmsg.setMessageState(MessageState.REPLIED);
//				xmsg.setFrom(from);
//				xmsg.setTo(to);
//				xmsg.setPayload(payload);
//				xmsg.setTimestamp(Timestamp.valueOf(LocalDateTime.now()).getTime());
		
//		XMessageDAOUtils utils = new XMessageDAOUtils();
//		XMessageDAO dao = XMessageDAOUtils.convertXMessageToDAO(xmsg);
//		System.out.println(dao);
	}
}
