/**
 * DeveloperVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.br.service.valueObject;

public class DeveloperVO  implements java.io.Serializable {
    private boolean active;

    private java.lang.Boolean coordination;

    private java.lang.String name;

    private java.lang.String passWord;

    private com.br.service.valueObject.ProjectVO projectVO;

    private java.lang.String userName;

    public DeveloperVO() {
    }

    public DeveloperVO(
           boolean active,
           java.lang.Boolean coordination,
           java.lang.String name,
           java.lang.String passWord,
           com.br.service.valueObject.ProjectVO projectVO,
           java.lang.String userName) {
           this.active = active;
           this.coordination = coordination;
           this.name = name;
           this.passWord = passWord;
           this.projectVO = projectVO;
           this.userName = userName;
    }


    /**
     * Gets the active value for this DeveloperVO.
     * 
     * @return active
     */
    public boolean isActive() {
        return active;
    }


    /**
     * Sets the active value for this DeveloperVO.
     * 
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }


    /**
     * Gets the coordination value for this DeveloperVO.
     * 
     * @return coordination
     */
    public java.lang.Boolean getCoordination() {
        return coordination;
    }


    /**
     * Sets the coordination value for this DeveloperVO.
     * 
     * @param coordination
     */
    public void setCoordination(java.lang.Boolean coordination) {
        this.coordination = coordination;
    }


    /**
     * Gets the name value for this DeveloperVO.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this DeveloperVO.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the passWord value for this DeveloperVO.
     * 
     * @return passWord
     */
    public java.lang.String getPassWord() {
        return passWord;
    }


    /**
     * Sets the passWord value for this DeveloperVO.
     * 
     * @param passWord
     */
    public void setPassWord(java.lang.String passWord) {
        this.passWord = passWord;
    }


    /**
     * Gets the projectVO value for this DeveloperVO.
     * 
     * @return projectVO
     */
    public com.br.service.valueObject.ProjectVO getProjectVO() {
        return projectVO;
    }


    /**
     * Sets the projectVO value for this DeveloperVO.
     * 
     * @param projectVO
     */
    public void setProjectVO(com.br.service.valueObject.ProjectVO projectVO) {
        this.projectVO = projectVO;
    }


    /**
     * Gets the userName value for this DeveloperVO.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this DeveloperVO.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeveloperVO)) return false;
        DeveloperVO other = (DeveloperVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.active == other.isActive() &&
            ((this.coordination==null && other.getCoordination()==null) || 
             (this.coordination!=null &&
              this.coordination.equals(other.getCoordination()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.passWord==null && other.getPassWord()==null) || 
             (this.passWord!=null &&
              this.passWord.equals(other.getPassWord()))) &&
            ((this.projectVO==null && other.getProjectVO()==null) || 
             (this.projectVO!=null &&
              this.projectVO.equals(other.getProjectVO()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName())));
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
        _hashCode += (isActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCoordination() != null) {
            _hashCode += getCoordination().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPassWord() != null) {
            _hashCode += getPassWord().hashCode();
        }
        if (getProjectVO() != null) {
            _hashCode += getProjectVO().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeveloperVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://valueObject.service.br.com", "DeveloperVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("active");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "active"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "coordination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passWord");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "passWord"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectVO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "projectVO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://valueObject.service.br.com", "ProjectVO"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://valueObject.service.br.com", "userName"));
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
