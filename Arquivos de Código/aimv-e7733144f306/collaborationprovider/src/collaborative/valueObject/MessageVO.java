/**
 * MessageVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package collaborative.valueObject;

public class MessageVO  implements java.io.Serializable {
    private java.lang.String activity;

    private java.lang.String date;

    private collaborative.valueObject.DeveloperVO developerVO;

    private java.lang.String entity;

    private java.lang.String finalContent;

    private java.lang.String finalTime;

    private java.lang.String initialContent;

    private java.lang.String initialTime;

    private java.lang.String paradigm;

    private java.lang.String text;

    public MessageVO() {
    }

    public MessageVO(
           java.lang.String activity,
           java.lang.String date,
           collaborative.valueObject.DeveloperVO developerVO,
           java.lang.String entity,
           java.lang.String finalContent,
           java.lang.String finalTime,
           java.lang.String initialContent,
           java.lang.String initialTime,
           java.lang.String paradigm,
           java.lang.String text) {
           this.activity = activity;
           this.date = date;
           this.developerVO = developerVO;
           this.entity = entity;
           this.finalContent = finalContent;
           this.finalTime = finalTime;
           this.initialContent = initialContent;
           this.initialTime = initialTime;
           this.paradigm = paradigm;
           this.text = text;
    }


    /**
     * Gets the activity value for this MessageVO.
     * 
     * @return activity
     */
    public java.lang.String getActivity() {
        return activity;
    }


    /**
     * Sets the activity value for this MessageVO.
     * 
     * @param activity
     */
    public void setActivity(java.lang.String activity) {
        this.activity = activity;
    }


    /**
     * Gets the date value for this MessageVO.
     * 
     * @return date
     */
    public java.lang.String getDate() {
        return date;
    }


    /**
     * Sets the date value for this MessageVO.
     * 
     * @param date
     */
    public void setDate(java.lang.String date) {
        this.date = date;
    }


    /**
     * Gets the developerVO value for this MessageVO.
     * 
     * @return developerVO
     */
    public collaborative.valueObject.DeveloperVO getDeveloperVO() {
        return developerVO;
    }


    /**
     * Sets the developerVO value for this MessageVO.
     * 
     * @param developerVO
     */
    public void setDeveloperVO(collaborative.valueObject.DeveloperVO developerVO) {
        this.developerVO = developerVO;
    }


    /**
     * Gets the entity value for this MessageVO.
     * 
     * @return entity
     */
    public java.lang.String getEntity() {
        return entity;
    }


    /**
     * Sets the entity value for this MessageVO.
     * 
     * @param entity
     */
    public void setEntity(java.lang.String entity) {
        this.entity = entity;
    }


    /**
     * Gets the finalContent value for this MessageVO.
     * 
     * @return finalContent
     */
    public java.lang.String getFinalContent() {
        return finalContent;
    }


    /**
     * Sets the finalContent value for this MessageVO.
     * 
     * @param finalContent
     */
    public void setFinalContent(java.lang.String finalContent) {
        this.finalContent = finalContent;
    }


    /**
     * Gets the finalTime value for this MessageVO.
     * 
     * @return finalTime
     */
    public java.lang.String getFinalTime() {
        return finalTime;
    }


    /**
     * Sets the finalTime value for this MessageVO.
     * 
     * @param finalTime
     */
    public void setFinalTime(java.lang.String finalTime) {
        this.finalTime = finalTime;
    }


    /**
     * Gets the initialContent value for this MessageVO.
     * 
     * @return initialContent
     */
    public java.lang.String getInitialContent() {
        return initialContent;
    }


    /**
     * Sets the initialContent value for this MessageVO.
     * 
     * @param initialContent
     */
    public void setInitialContent(java.lang.String initialContent) {
        this.initialContent = initialContent;
    }


    /**
     * Gets the initialTime value for this MessageVO.
     * 
     * @return initialTime
     */
    public java.lang.String getInitialTime() {
        return initialTime;
    }


    /**
     * Sets the initialTime value for this MessageVO.
     * 
     * @param initialTime
     */
    public void setInitialTime(java.lang.String initialTime) {
        this.initialTime = initialTime;
    }


    /**
     * Gets the paradigm value for this MessageVO.
     * 
     * @return paradigm
     */
    public java.lang.String getParadigm() {
        return paradigm;
    }


    /**
     * Sets the paradigm value for this MessageVO.
     * 
     * @param paradigm
     */
    public void setParadigm(java.lang.String paradigm) {
        this.paradigm = paradigm;
    }


    /**
     * Gets the text value for this MessageVO.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this MessageVO.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessageVO)) return false;
        MessageVO other = (MessageVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.activity==null && other.getActivity()==null) || 
             (this.activity!=null &&
              this.activity.equals(other.getActivity()))) &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.developerVO==null && other.getDeveloperVO()==null) || 
             (this.developerVO!=null &&
              this.developerVO.equals(other.getDeveloperVO()))) &&
            ((this.entity==null && other.getEntity()==null) || 
             (this.entity!=null &&
              this.entity.equals(other.getEntity()))) &&
            ((this.finalContent==null && other.getFinalContent()==null) || 
             (this.finalContent!=null &&
              this.finalContent.equals(other.getFinalContent()))) &&
            ((this.finalTime==null && other.getFinalTime()==null) || 
             (this.finalTime!=null &&
              this.finalTime.equals(other.getFinalTime()))) &&
            ((this.initialContent==null && other.getInitialContent()==null) || 
             (this.initialContent!=null &&
              this.initialContent.equals(other.getInitialContent()))) &&
            ((this.initialTime==null && other.getInitialTime()==null) || 
             (this.initialTime!=null &&
              this.initialTime.equals(other.getInitialTime()))) &&
            ((this.paradigm==null && other.getParadigm()==null) || 
             (this.paradigm!=null &&
              this.paradigm.equals(other.getParadigm()))) &&
            ((this.text==null && other.getText()==null) || 
             (this.text!=null &&
              this.text.equals(other.getText())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getActivity() != null) {
            _hashCode += getActivity().hashCode();
        }
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getDeveloperVO() != null) {
            _hashCode += getDeveloperVO().hashCode();
        }
        if (getEntity() != null) {
            _hashCode += getEntity().hashCode();
        }
        if (getFinalContent() != null) {
            _hashCode += getFinalContent().hashCode();
        }
        if (getFinalTime() != null) {
            _hashCode += getFinalTime().hashCode();
        }
        if (getInitialContent() != null) {
            _hashCode += getInitialContent().hashCode();
        }
        if (getInitialTime() != null) {
            _hashCode += getInitialTime().hashCode();
        }
        if (getParadigm() != null) {
            _hashCode += getParadigm().hashCode();
        }
        if (getText() != null) {
            _hashCode += getText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessageVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://valueObject.service.br.com", "MessageVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "activity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("developerVO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "developerVO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://valueObject.service.br.com", "DeveloperVO"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "entity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "finalContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "finalTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("initialContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "initialContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("initialTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "initialTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paradigm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "paradigm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("text");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "text"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
