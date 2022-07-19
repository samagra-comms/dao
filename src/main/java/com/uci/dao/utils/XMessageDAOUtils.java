package com.uci.dao.utils;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uci.dao.models.XMessageDAO;
import messagerosa.core.model.XMessage;

import javax.xml.bind.JAXBException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class XMessageDAOUtils {

    public static ObjectMapper mapper = new ObjectMapper();

    public static XMessageDAO convertXMessageToDAO(XMessage xmsg) {
        XMessageDAO xmsgDao = new XMessageDAO();

        try {
            xmsgDao.setUserId(xmsg.getTo().getUserID());
            if (xmsg.getTo().getUserID().equals("Bulk")) {
                xmsgDao.setReplyId("");
                xmsgDao.setMessageId(xmsg.getMessageId().getChannelMessageId());
            } else {
                xmsgDao.setReplyId(xmsg.getMessageId().getReplyId());
                if (xmsg.getMessageId() != null && xmsg.getMessageId().getChannelMessageId() != null) {
                    try {
                        xmsgDao.setMessageId(xmsg.getMessageId().getChannelMessageId().toString());
                        xmsgDao.setCauseId(xmsg.getMessageId().getChannelMessageId().toString());
                    } catch (Exception e) {
                        xmsgDao.setMessageId(xmsg.getMessageId().getChannelMessageId());
                    }
                }
            }
        } catch (Exception e) {
            // Bulk SMS so no replyID
        }

        try {
            System.out.println(mapper.writeValueAsString(xmsg));
            xmsgDao.setXMessage(xmsg.toXML());
        } catch (JAXBException | JsonProcessingException e) {
            e.printStackTrace();
        }

        xmsgDao.setFromId(xmsg.getFrom().getUserID());
        xmsgDao.setChannel(xmsg.getChannelURI());
        xmsgDao.setProvider(xmsg.getProviderURI());
        xmsgDao.setSessionId(xmsg.getSessionId());
        xmsgDao.setOwnerOrgId(xmsg.getOwnerOrgId());
        xmsgDao.setOwnerId(xmsg.getOwnerId());
        xmsgDao.setBotUuid(xmsg.getBotUuid());

        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(xmsg.getTimestamp()),
                        TimeZone.getDefault().toZoneId());

        xmsgDao.setTimestamp(LocalDateTime.from(triggerTime));
        xmsgDao.setId(UUIDs.timeBased());

        if (xmsg.getMessageState() != null) {
            xmsgDao.setMessageState(xmsg.getMessageState().name());
        }

        xmsgDao.setApp(xmsg.getApp());
        return xmsgDao;
    }

}
