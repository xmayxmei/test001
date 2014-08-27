/*
 * SimpleMAPI.java
 *
 * Created on 26. Juni 2008, 14:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util.mapi;

import java.io.File;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.HANDLE;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.util.mapi.structs.MapiFileDesc;
import org.xvolks.jnative.util.mapi.structs.MapiMessage;
import org.xvolks.jnative.util.mapi.structs.MapiRecipDesc;
import static org.xvolks.jnative.util.mapi.MAPIConstants.*;

/**
 *
 * @author __USER__
 */
public class SimpleMAPI
{
    private static final String DLL_NAME = "MAPI32.DLL";
    
    /**
     * Creates a new instance of SimpleMAPI
     */
    public SimpleMAPI()
    {
    }
    public static int MAPISaveMail(HANDLE lhSession, LONG ulUIParam, MapiMessage lpMessage, int flFlags, LONG ulReserved, Pointer lpszMessageID) throws NativeException, IllegalAccessException
    {
        JNative nMAPISaveMail = new JNative(DLL_NAME, "MAPISaveMail");
        nMAPISaveMail.setRetVal(Type.INT);
        
        int i = 0;
        nMAPISaveMail.setParameter(i++, lhSession.getValue());
        nMAPISaveMail.setParameter(i++, ulUIParam.getValue());
        nMAPISaveMail.setParameter(i++, lpMessage.createPointer().getPointer());
        nMAPISaveMail.setParameter(i++, flFlags);
        nMAPISaveMail.setParameter(i++, ulReserved.getValue());
        nMAPISaveMail.setParameter(i++, lpszMessageID.getPointer());
        
        nMAPISaveMail.invoke();
        return nMAPISaveMail.getRetValAsInt();
    }
    
    public static int MAPIAddress(HANDLE lhSession, LONG ulUIParam, Pointer lpszCaption, LONG nEditFields, Pointer lpszLabels, LONG nRecips, MapiRecipDesc lpRecips, int flFlags, LONG ulReserved, LONG lpnNewRecips, LONG lppNewRecips) 
        throws NativeException, IllegalAccessException
    {
        JNative nMAPIAddress = new JNative(DLL_NAME, "MAPIAddress");
        nMAPIAddress.setRetVal(Type.INT);
        
        int i = 0;
        nMAPIAddress.setParameter(i++, lhSession.getValue());
        nMAPIAddress.setParameter(i++, ulUIParam.getValue());
        nMAPIAddress.setParameter(i++, lpszCaption.getPointer());
        nMAPIAddress.setParameter(i++, nEditFields.getValue());
        nMAPIAddress.setParameter(i++, lpszLabels.getPointer());
        nMAPIAddress.setParameter(i++, nRecips.getValue());
        nMAPIAddress.setParameter(i++, lpRecips.createPointer().getPointer());
        nMAPIAddress.setParameter(i++, flFlags);
        nMAPIAddress.setParameter(i++, ulReserved.getValue());
        nMAPIAddress.setParameter(i++, lpnNewRecips.getPointer());
        nMAPIAddress.setParameter(i++, lppNewRecips.getPointer());
        
        nMAPIAddress.invoke();
        return nMAPIAddress.getRetValAsInt();
    }
    
    public static int MAPIDeleteMail(HANDLE lhSession, LONG ulUIParam, Pointer lpszMessageID, int flFlags, LONG ulReserved) throws NativeException, IllegalAccessException
    {
        JNative nMAPIDeleteMail = new JNative(DLL_NAME, "MAPIDeleteMail");
        nMAPIDeleteMail.setRetVal(Type.INT);
        
        int i = 0;
        nMAPIDeleteMail.setParameter(i++, lhSession.getValue());
        nMAPIDeleteMail.setParameter(i++, ulUIParam.getValue());
        nMAPIDeleteMail.setParameter(i++, lpszMessageID.getPointer());
        nMAPIDeleteMail.setParameter(i++, flFlags);
        nMAPIDeleteMail.setParameter(i++, ulReserved.getValue());
        
        nMAPIDeleteMail.invoke();
        return nMAPIDeleteMail.getRetValAsInt();
    }
    
    public static int MAPIDetails(HANDLE lhSession, LONG ulUIParam, MapiRecipDesc lpRecip, int flFlags, LONG ulReserved) throws NativeException, IllegalAccessException
    {
        JNative nMAPIDetails = new JNative(DLL_NAME, "MAPIDetails");
        nMAPIDetails.setRetVal(Type.INT);
        
        int i = 0;
        nMAPIDetails.setParameter(i++, lhSession.getValue());
        nMAPIDetails.setParameter(i++, ulUIParam.getValue());
        nMAPIDetails.setParameter(i++, lpRecip.createPointer().getPointer());
        nMAPIDetails.setParameter(i++, flFlags);
        nMAPIDetails.setParameter(i++, ulReserved.getValue());
        
        nMAPIDetails.invoke();
        return nMAPIDetails.getRetValAsInt();
    }
    
    public static int MAPIReadMail(HANDLE lhSession, LONG ulUIParam, Pointer lpszMessageID, int flFlags, LONG ulReserved, LONG lppMessage) throws NativeException, IllegalAccessException
    {
        JNative nMAPIReadMail = new JNative(DLL_NAME, "MAPIReadMail");
        nMAPIReadMail.setRetVal(Type.INT);
        
        int i = 0;
        nMAPIReadMail.setParameter(i++, lhSession.getValue());
        nMAPIReadMail.setParameter(i++, ulUIParam.getValue());
        nMAPIReadMail.setParameter(i++, lpszMessageID.getPointer());
        nMAPIReadMail.setParameter(i++, flFlags);
        nMAPIReadMail.setParameter(i++, ulReserved.getValue());
        nMAPIReadMail.setParameter(i++, lppMessage.getPointer());
        
        nMAPIReadMail.invoke();
        return nMAPIReadMail.getRetValAsInt();
    }
    public static int MAPIFindNext(HANDLE lhSession, LONG ulUIParam, Pointer lpszMessageType, Pointer lpszSeedMessageID, int flFlags, LONG ulReserved, Pointer lpszMessageID) throws NativeException, IllegalAccessException
    {
        JNative nMAPIFindNext = new JNative(DLL_NAME, "MAPIFindNext");
        nMAPIFindNext.setRetVal(Type.INT);
        
        int i = 0;
        nMAPIFindNext.setParameter(i++, lhSession.getValue());
        nMAPIFindNext.setParameter(i++, ulUIParam.getValue());
        nMAPIFindNext.setParameter(i++, lpszMessageType.getPointer());
        nMAPIFindNext.setParameter(i++, lpszSeedMessageID.getPointer());
        nMAPIFindNext.setParameter(i++, flFlags);
        nMAPIFindNext.setParameter(i++, ulReserved.getValue());
        nMAPIFindNext.setParameter(i++, lpszMessageID.getPointer());
        
        nMAPIFindNext.invoke();
        return nMAPIFindNext.getRetValAsInt();
    }
    public static int MAPIFreeBuffer(int pv) throws NativeException, IllegalAccessException
    {
        JNative nMAPIFreeBuffer = null;
        
        nMAPIFreeBuffer = new JNative(DLL_NAME, "MAPIFreeBuffer");
        nMAPIFreeBuffer.setRetVal(Type.INT);
        
        nMAPIFreeBuffer.setParameter(0, pv);
        
        nMAPIFreeBuffer.invoke();
        return nMAPIFreeBuffer.getRetValAsInt();
        
    }
    
    public static int MAPIResolveName(HANDLE lhSession, LONG ulUIParam, Pointer lpszName, int flFlags, LONG ulReserved, LONG lppRecip) throws NativeException, IllegalAccessException
    {
        JNative nMAPIResolveName = new JNative(DLL_NAME, "MAPIResolveName");
        nMAPIResolveName.setRetVal(Type.INT);
        
        int i = 0;
        nMAPIResolveName.setParameter(i++, lhSession.getValue());
        nMAPIResolveName.setParameter(i++, ulUIParam.getValue());
        nMAPIResolveName.setParameter(i++, lpszName.getPointer());
        nMAPIResolveName.setParameter(i++, flFlags);
        nMAPIResolveName.setParameter(i++, ulReserved.getValue());
        nMAPIResolveName.setParameter(i++, lppRecip.getPointer());
        
        nMAPIResolveName.invoke();
        return nMAPIResolveName.getRetValAsInt();
    }
    
    public static int MAPILogoff(HANDLE lhSession, LONG ulUIParam,  int flFlags) throws NativeException, IllegalAccessException
    {
        JNative nMAPILogoff = new JNative(DLL_NAME, "MAPILogoff");
        nMAPILogoff.setRetVal(Type.INT);
        int i = 0;
        nMAPILogoff.setParameter(i++, lhSession.getValue());
        nMAPILogoff.setParameter(i++, ulUIParam.getValue());
        nMAPILogoff.setParameter(i++, flFlags);
        nMAPILogoff.setParameter(i++, 0);
        
        nMAPILogoff.invoke();
        return nMAPILogoff.getRetValAsInt();
        
    }
    public static int MAPILogon(LONG ulUIParam, Pointer lpszProfileName, Pointer lpszPassword, int flFlags, HANDLE lplhSession) throws NativeException, IllegalAccessException
    {
        JNative nMAPILogon = new JNative(DLL_NAME, "MAPILogon");
        nMAPILogon.setRetVal(Type.INT);
        int i = 0;
        nMAPILogon.setParameter(i++, ulUIParam.getValue());
        nMAPILogon.setParameter(i++, lpszProfileName.getPointer());
        nMAPILogon.setParameter(i++, lpszPassword.getPointer());
        nMAPILogon.setParameter(i++, flFlags);
        nMAPILogon.setParameter(i++, 0);
        nMAPILogon.setParameter(i++, lplhSession.getPointer().getPointer());
        
        nMAPILogon.invoke();
        return nMAPILogon.getRetValAsInt();
    }
    public static final String MAPISendMail( HANDLE lhSession, LONG ulUIParam, MapiMessage lpMessage, int flFlags) throws NativeException, IllegalAccessException
    {
        JNative nMAPISendMail = new JNative(DLL_NAME, "MAPISendMail");
        nMAPISendMail.setRetVal(Type.INT);
        
        int i = 0;
        nMAPISendMail.setParameter(i++, lhSession.getValue());
        nMAPISendMail.setParameter(i++, ulUIParam.getValue());
        // call createPointer() not getPointer() to set the data correctly!!
        nMAPISendMail.setParameter(i++, lpMessage.createPointer().getPointer());
        nMAPISendMail.setParameter(i++, flFlags);
        nMAPISendMail.setParameter(i++, 0);
        
        nMAPISendMail.invoke();
        
        return nMAPISendMail.getRetVal();
        
    }
    
    private static void attachmentTest()
    {
        try
        {
            // session for the whole MAPI stuff
            HANDLE session = new HANDLE(0);

            // Outlook profile name
            // if there is only one profile you might pass also a nullpointer (not sure)
            Pointer lpszProfile = Pointer.createPointerFromString("Outlook");
            // logon
            int ret = MAPILogon(new LONG(0), lpszProfile, NullPointer.NULL, 0, session);
            System.out.println("MAPILogon: "+ret+", session: "+session.getValue());

            if(ret == SUCCESS_SUCCESS)
            {

                // create Pointer to hold the message id
                // as MAPI_LONG_MSGID is set we need to allocate 512 bytes + 1 byte (for null-terminator)
                Pointer rgchMsgID = Pointer.createPointer(513);
                Pointer lpszSeedMessageID = Pointer.createPointer(513);
                
                while(true)
                {
                    // zero memory
                    rgchMsgID.zeroMemory();

                    // find the first/next message
                    ret = MAPIFindNext(session, new LONG(0), NullPointer.NULL, lpszSeedMessageID, MAPI_LONG_MSGID, new LONG(0), rgchMsgID);
                    System.out.println("MAPIFindNext: "+ret);
                    
                    if(ret == SUCCESS_SUCCESS)
                    {
                        System.out.println("rgchMsgID:" +rgchMsgID.getAsString());
                        System.out.println("lpszSeedMessageID:" +lpszSeedMessageID.getAsString());

                        // allocate a LONG to retrieve the pointer address to the MapiMessage struct
                        LONG msg = new LONG(0);
                        // read the message
                        ret = MAPIReadMail(session, new LONG(0), rgchMsgID, 0, new LONG(0), msg);
                        System.out.println("MAPIReadMail: "+ret);

                        if(ret == SUCCESS_SUCCESS)
                        {
                            // create a MapiMessage struct and pass the pointer to automatically fill the struct
                            MapiMessage _msg = new MapiMessage(msg.getValueFromPointer());

                            // get the values
                            System.out.println(JNative.getMemoryAsString(_msg.lpszSubject));
                            System.out.println(JNative.getMemoryAsString(_msg.lpszNoteText));
                            System.out.println("nFileCount: "+_msg.nFileCount.getValue());

                            // check attachments
                            if(_msg.nFileCount.getValue() > 0)
                            {
                                MapiFileDesc fileDesc = null;
                                // loop over all attachments
                                for(int i = 0; i<_msg.nFileCount.getValue(); i++)
                                {
                                    // create a MapiFileDesc struct and pass the pointer address at _msg.lpFiles + MapiFileDesc.sizeOf() for each loop step
                                    fileDesc = new MapiFileDesc(_msg.lpFiles+(i*MapiFileDesc.sizeOf()));
                                    System.out.println(JNative.getMemoryAsString(fileDesc.lpszPathName));
                                    // we are responsible that the file gets deleted!
                                    new File(JNative.getMemoryAsString(fileDesc.lpszPathName)).delete();
                                }

                                // break if we found a message with more than 1 attachment
                                if(_msg.nFileCount.getValue() > 1)
                                {
                                    break;
                                }
                            }
                        }
                        System.out.println("MAPIFreeBuffer: "+MAPIFreeBuffer(msg.getValue()));
                    }
                    else
                    {
                        break;
                    }

                    // copy rgchMsgID to lpszSeedMessageID to find the next message
                    // if we dont do this we will always get the first message
                    JNative.copyMemory(rgchMsgID, lpszSeedMessageID);
                }
                // cleanup
                rgchMsgID.dispose();
                lpszSeedMessageID.dispose();
            }
            
            // logoff
            System.out.println("MAPILogoff: "+MAPILogoff(session, new LONG(0), 0));
            lpszProfile.dispose();
        }
        catch (NativeException ex)
        {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            JNative.unLoadAllLibraries();
        }
    }
    
    public static void main(String args[])
    {
        try
        {
            JNative.setLoggingEnabled(true);
            
            // session for the whole MAPI stuff
            HANDLE session = new HANDLE(0);
            
            // Outlook profile name
            // if there is only one profile you might pass also a nullpointer (not sure)
            Pointer lpszProfile = Pointer.createPointerFromString("Outlook");
            // logon
            int ret = MAPILogon(new LONG(0), lpszProfile, NullPointer.NULL, 0, session);
            System.out.println("MAPILogon: "+ret+", session: "+session.getValue());
            
            if(ret == SUCCESS_SUCCESS)
            {
                // create a MapiMessage Struct
                MapiMessage MapiMessage = new MapiMessage();
                
                // set subject and message
                Pointer lpszSubject = Pointer.createPointerFromString("Test Subject");
                Pointer lpszNoteText = Pointer.createPointerFromString("Test Message");
                
                // add the pointers to the MapiMessage struct
                MapiMessage.lpszSubject = lpszSubject.getPointer();
                MapiMessage.lpszNoteText = lpszNoteText.getPointer();
                
                // resolve the receipient 1
                LONG MapiRecipDescHolder1 = new LONG(0);
                Pointer lpszName = Pointer.createPointerFromString("XYZ");
                
                // resolve email and stuff 1
                ret = MAPIResolveName(session, new LONG(0), lpszName, 0, new LONG(0), MapiRecipDescHolder1);
                System.out.println("MAPIResolveName: "+ret);
                
                if(ret == SUCCESS_SUCCESS)
                {
                    // if only one match is returned we can grab the pointer to the MapiRecipDesc struct
                    System.out.println("Pointer to MapiRecipDesc[0]: "+MapiRecipDescHolder1.getValueFromPointer());
                    
                    // create MapiRecipDesc struct and fill it with data by passing the pointer
                    MapiRecipDesc MapiRecipDesc = new MapiRecipDesc(MapiRecipDescHolder1.getValueFromPointer());
                    
                    LONG lpnNewRecips = new LONG(0);
                    LONG lppNewRecips = new LONG(0);
                    // if you want to pass more than one Receipient to the list you need to create a pointer that holds the data of all MapiRecipDesc structs. See MapiSendMail() example.
                    ret = MAPIAddress(session, new LONG(0), NullPointer.NULL, new LONG(0), NullPointer.NULL, new LONG(1), MapiRecipDesc, 0, new LONG(0), lpnNewRecips, lppNewRecips);
                    System.out.println("MAPIAddress: "+ret);
                    
                    if(ret == SUCCESS_SUCCESS)
                    {
                        System.out.println("lpnNewRecips: "+lpnNewRecips.getValue());
                        
                        MapiRecipDesc receipDesc = null;
                        for(int i = 0; i<lpnNewRecips.getValue(); i++)
                        {
                            // create a MapiRecipDesc struct and pass the pointer address at lppNewRecips.getValueFromPointer()+ MapiRecipDesc.sizeOf() for each loop step
                            receipDesc = new MapiRecipDesc(lppNewRecips.getValueFromPointer()+(i*MapiRecipDesc.sizeOf()));
                            System.out.println(JNative.getMemoryAsString(receipDesc.lpszName));
                        }
                        // free the memory retrieved by MAPIResolveName
                        System.out.println("Free: "+MAPIFreeBuffer(lppNewRecips.getValue()));
                    }
                    
                    // display details of this user
                    ret = MAPIDetails(session, new LONG(0), MapiRecipDesc, 0, new LONG(0));
                    
                    // if not pressed "OK" return
                    if(ret != SUCCESS_SUCCESS)
                    {
                         // logoff
                        System.out.println("MAPILogoff: "+MAPILogoff(session, new LONG(0), 0));
                        // free the memory retrieved by MAPIResolveName
                        System.out.println("Free: "+MAPIFreeBuffer(MapiRecipDescHolder1.getValueFromPointer()));
                        return;
                    }
                    
                    // resolve the receipient 2
                    LONG MapiRecipDescHolder2 = new LONG(0);
                    Pointer lpszName2 = Pointer.createPointerFromString("XXX");
                    
                    // resolve email and stuff 2
                    ret = MAPIResolveName(session, new LONG(0), lpszName2, 0, new LONG(0), MapiRecipDescHolder2);
                    System.out.println("MAPIResolveName: "+ret);
                    
                    if(ret == SUCCESS_SUCCESS)
                    {
                        // if only one match is returned we can grab the pointer to the MapiRecipDesc struct
                        System.out.println("Pointer to MapiRecipDesc2[0]: "+MapiRecipDescHolder2.getValueFromPointer());
                        
                        // we want to change the second receipent to be on CC:
                        MapiRecipDesc MapiRecipDesc2 = new MapiRecipDesc(MapiRecipDescHolder2.getValueFromPointer());
                        
                        // you can also use the empty constructor and do the following:
                        /*
                         MapiRecipDesc MapiRecipDesc = new MapiRecipDesc();
                         MapiRecipDesc.getPointer().setMemory(JNative.getMemory(MapiRecipDescHolder2.getValueFromPointer(), MapiRecipDesc.sizeOf()));
                         // if you want to change the values inside MapiRecipDesc struct, call the following first:
                         MapiRecipDesc.getValueFromPointer();
                         */
                        
                        // then make the changes
                        MapiRecipDesc2.ulRecipClass = new LONG(MAPI_CC);
                        // you could also directly modify the memory at the correct offset...
                        
                        // now we have to create a pointer that holds both MapiRecipDesc structs in its memory
                        // It does NOT hold the pointer addresses to the MapiRecipDesc structs, but the real data!
                        // Confused me a little, i think a pointer to pointers would have been nicer here...
                        Pointer MapiRecipDescArray = Pointer.createPointer(2*MapiRecipDesc.sizeOf());
                        // call createPointer() to fill the pointer with the previously changed data
                        MapiRecipDescArray.setMemory(MapiRecipDesc2.createPointer().getMemory());
                        // for the second struct we have to _append_ the memory so we have to call JNative.setMemory() with the correct offset
                        JNative.setMemory(MapiRecipDescArray.getPointer(), JNative.getMemory(MapiRecipDescHolder1.getValueFromPointer(), MapiRecipDesc.sizeOf()), MapiRecipDesc.sizeOf(), 2*MapiRecipDesc.sizeOf()-1);
                        
                        // pass the MapiRecipDesc to MapiMessage struct
                        MapiMessage.nRecipCount = new LONG(2);
                        MapiMessage.lpRecips = MapiRecipDescArray.getPointer();
                        // if you only want to send to one receipent set nReceiptCount to 1 and pass the pointer address to the MapiRecipDesc struct at index 0 from the pointer you received from MAPIResolveName
                        // MapiMessage.lpRecips = MapiRecipDescHolder1.getAsInt(0);
                        
                        // create an attachment
                        Pointer filePath = Pointer.createPointerFromString("c:\\dp.txt");
                        MapiFileDesc MapiFileDesc = new MapiFileDesc();
                        MapiFileDesc.lpszPathName = filePath.getPointer();
                        MapiFileDesc.nPosition = new LONG(-1);
                        
                        // add the attachment to the Mail
                        MapiMessage.nFileCount = new LONG(1);
                        // call createPointer() not getPointer() to set the data correctly!!
                        MapiMessage.lpFiles = MapiFileDesc.createPointer().getPointer();
                        // if you want to send multiple attachments you have to do the same like with multiple receipients:
                        // create a pointer that holds the memory of the MapiFileDesc structs. Not a pointer to pointers!
                        
                        // send the message
                        System.out.println("MAPISendMail: "+MAPISendMail(session, new LONG(0), MapiMessage, MAPI_DIALOG ));
                        
                        // free native resources
                        MapiRecipDescArray.dispose();
                        MapiFileDesc.getPointer().dispose();
                        MapiRecipDesc2.getPointer().dispose();
                        filePath.dispose();
                    }
                    
                    // free the memory retrieved by MAPIResolveName
                    System.out.println("Free: "+MAPIFreeBuffer(MapiRecipDescHolder1.getValueFromPointer()));
                    System.out.println("Free: "+MAPIFreeBuffer(MapiRecipDescHolder2.getValueFromPointer()));
                }
                
                // logoff
                System.out.println("MAPILogoff: "+MAPILogoff(session, new LONG(0), 0));
                
                lpszNoteText.dispose();
                lpszSubject.dispose();
                lpszName.dispose();
                MapiMessage.getPointer().dispose();
                lpszProfile.dispose();
                
            }
        }
        catch (NativeException ex)
        {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            JNative.unLoadAllLibraries();
        }
    }
    
}
