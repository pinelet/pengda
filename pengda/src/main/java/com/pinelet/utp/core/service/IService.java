/***********************************************************************
 * Module:  IService.java
 * Author:  Administrator
 * Purpose: Defines the Interface IService
 ***********************************************************************/

package com.pinelet.utp.core.service;

import java.util.*;

import org.apache.mina.core.session.IoSession;

/** 设备请求报文处理
 * 
 * @pdOid 49c5bbdc-fa85-4f82-8130-78fe5d36b779 */
public interface IService {
   /** @param request
    * @pdOid e0542778-f8c7-4342-91f3-dd612d3ccc6a */
   String operation(IoSession session, java.lang.String[] request);
   
}