/*
 * MAPIConstants.java
 *
 * Created on 26. Juni 2008, 16:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util.mapi;

/**
 *
 * @author __USER__
 */
public class MAPIConstants
{
    
    public static final int SUCCESS_SUCCESS = 0;
    public static final int MAPI_USER_ABORT = 1;
    public static final int MAPI_E_USER_ABORT = MAPI_USER_ABORT;
    public static final int MAPI_E_FAILURE = 2;
    public static final int MAPI_E_LOGIN_FAILURE = 3;
    public static final int MAPI_E_LOGON_FAILURE = MAPI_E_LOGIN_FAILURE;
    public static final int MAPI_E_DISK_FULL = 4;
    public static final int MAPI_E_INSUFFICIENT_MEMORY = 5;
    public static final int MAPI_E_BLK_TOO_SMALL = 6;
    public static final int MAPI_E_TOO_MANY_SESSIONS = 8;
    public static final int MAPI_E_TOO_MANY_FILES = 9;
    public static final int MAPI_E_TOO_MANY_RECIPIENTS = 10;
    public static final int MAPI_E_ATTACHMENT_NOT_FOUND = 11;
    public static final int MAPI_E_ATTACHMENT_OPEN_FAILURE = 12;
    public static final int MAPI_E_ATTACHMENT_WRITE_FAILURE = 13;
    public static final int MAPI_E_UNKNOWN_RECIPIENT = 14;
    public static final int MAPI_E_BAD_RECIPTYPE = 15;
    public static final int MAPI_E_NO_MESSAGES = 16;
    public static final int MAPI_E_INVALID_MESSAGE = 17;
    public static final int MAPI_E_TEXT_TOO_LARGE = 18;
    public static final int MAPI_E_INVALID_SESSION = 19;
    public static final int MAPI_E_TYPE_NOT_SUPPORTED = 20;
    public static final int MAPI_E_AMBIGUOUS_RECIPIENT = 21;
    public static final int MAPI_E_AMBIG_RECIP = MAPI_E_AMBIGUOUS_RECIPIENT;
    public static final int MAPI_E_MESSAGE_IN_USE = 22;
    public static final int MAPI_E_NETWORK_FAILURE = 23;
    public static final int MAPI_E_INVALID_EDITFIELDS = 24;
    public static final int MAPI_E_INVALID_RECIPS = 25;
    public static final int MAPI_E_NOT_SUPPORTED = 26;

    public static final int MAPI_E_INVALID_PARAMETER = 998;
    public static final int MAPI_E_NO_LIBRARY = 999;

    public static final int MAPI_ORIG = 0;
    public static final int MAPI_TO = 1;
    public static final int MAPI_CC = 2;
    public static final int MAPI_BCC = 3;
    
    // MAPILogon() flags *
    public static final int MAPI_LOGON_UI = 0x1;
    public static final int MAPI_NEW_SESSION = 0x2;
    public static final int MAPI_FORCE_DOWNLOAD = 0x1000;

    // MAPILogoff() flags *
    public static final int MAPI_LOGOFF_SHARED = 0x1;
    public static final int MAPI_LOGOFF_UI = 0x2;

    // MAPISendMail() flags *
    public static final int MAPI_DIALOG = 0x8;

    // MAPIFindNext() flags *
    public static final int MAPI_LONG_MSGID = 0x4000;
    public static final int MAPI_UNREAD_ONLY = 0x20;
    public static final int MAPI_GUARANTEE_FIFO = 0x100;

    // MAPIReadMail() flags *
    public static final int MAPI_ENVELOPE_ONLY = 0x40;
    public static final int MAPI_PEEK = 0x80;
    public static final int MAPI_BODY_AS_FILE = 0x200;
    public static final int MAPI_SUPPRESS_ATTACH = 0x800;

    // MAPIDetails() flags *
    public static final int MAPI_AB_NOMODIFY = 0x400;

    // Attachment flags *
    public static final int MAPI_OLE = 0x1;
    public static final int MAPI_OLE_STATIC = 0x2;

    // MapiMessage flags *
    public static final int MAPI_UNREAD = 0x1;
    public static final int MAPI_RECEIPT_REQUESTED = 0x2;
    public static final int MAPI_SENT = 0x4;
    
}
