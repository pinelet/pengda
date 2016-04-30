/***********************************************************************
 * Module:  ReqMessage.java
 * Author:  Administrator
 * Purpose: Defines the Class ReqMessage
 ***********************************************************************/

package com.pinelet.utp.core.service;

import java.util.*;

/** @pdOid 868c225e-5fb1-4173-a289-f539f36ee577 */
public class ReqMessage extends Message {
   /** @pdOid 61e817af-bf34-4ce8-a1d3-21f4633d6887 */
   private String version;
   /** @pdOid af59b7db-46fb-41c6-bc7d-9922d7d42982 */
   private String imei;
   /** @pdOid b3d39516-4624-4555-b6bc-772970a1bde2 */
   private String optcode;
   /** @pdOid b274876e-a1eb-44f4-9444-a7edced40333 */
   private List data;
   /** @pdOid cd56e217-e52b-4492-8590-193863c5b7a6 */
   private String checksum;


/** @pdOid 65030b33-0bb6-4912-b849-0536700f4a23 */
   public String getVersion() {
      return version;
   }
   
   /** @param newVersion
    * @pdOid 879e6340-78e1-4f8f-aa27-4f6779c24756 */
   public void setVersion(String newVersion) {
      version = newVersion;
   }
   
   /** @pdOid 4080d887-29e3-4e67-8e86-a4990695cfb7 */
   public String getImei() {
      return imei;
   }
   
   /** @param newImei
    * @pdOid ff058a00-21cd-4453-a9de-703ea81aaaef */
   public void setImei(String newImei) {
      imei = newImei;
   }
   
   /** @pdOid 13ba958b-af00-4895-95a7-b6192f3e524f */
   public String getOptcode() {
      return optcode;
   }
   
   /** @param newOptcode
    * @pdOid 29ed3002-2927-4e99-8a93-c920333e4e63 */
   public void setOptcode(String newOptcode) {
      optcode = newOptcode;
   }
   
   /** @pdOid a826565c-a8f8-411f-a836-c80ff0f1ffe5 */
   public List getData() {
      return data;
   }
   
   /** @param newData
    * @pdOid ae52197a-1999-4b96-9cc8-767ac05f99c6 */
   public void setData(List newData) {
      data = newData;
   }
   
   /** @pdOid b4cf00ff-5470-455a-b49d-9659d6356b74 */
   public String getChecksum() {
      return checksum;
   }
   
   /** @param newChecksum
    * @pdOid e2e0cc7a-1e58-4dad-b9a3-e93b376399bd */
   public void setChecksum(String newChecksum) {
      checksum = newChecksum;
   }

}