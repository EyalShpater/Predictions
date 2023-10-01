//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.10.01 at 03:21:22 PM IDT 
//


package resources.xml.ex3.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}PRD-environment"/>
 *         &lt;element name="PRD-grid">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="columns" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="rows" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}PRD-entities"/>
 *         &lt;element ref="{}PRD-rules"/>
 *       &lt;/all>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sleep" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "PRD-world")
public class PRDWorld {

    @XmlElement(name = "PRD-environment", required = true)
    protected PRDEnvironment prdEnvironment;
    @XmlElement(name = "PRD-grid", required = true)
    protected PRDWorld.PRDGrid prdGrid;
    @XmlElement(name = "PRD-entities", required = true)
    protected PRDEntities prdEntities;
    @XmlElement(name = "PRD-rules", required = true)
    protected PRDRules prdRules;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "sleep")
    protected Integer sleep;

    /**
     * Gets the value of the prdEnvironment property.
     *
     * @return possible object is
     * {@link PRDEnvironment }
     */
    public PRDEnvironment getPRDEnvironment() {
        return prdEnvironment;
    }

    /**
     * Sets the value of the prdEnvironment property.
     *
     * @param value allowed object is
     *              {@link PRDEnvironment }
     */
    public void setPRDEnvironment(PRDEnvironment value) {
        this.prdEnvironment = value;
    }

    /**
     * Gets the value of the prdGrid property.
     *
     * @return possible object is
     * {@link PRDWorld.PRDGrid }
     */
    public PRDWorld.PRDGrid getPRDGrid() {
        return prdGrid;
    }

    /**
     * Sets the value of the prdGrid property.
     *
     * @param value allowed object is
     *              {@link PRDWorld.PRDGrid }
     */
    public void setPRDGrid(PRDWorld.PRDGrid value) {
        this.prdGrid = value;
    }

    /**
     * Gets the value of the prdEntities property.
     *
     * @return possible object is
     * {@link PRDEntities }
     */
    public PRDEntities getPRDEntities() {
        return prdEntities;
    }

    /**
     * Sets the value of the prdEntities property.
     *
     * @param value allowed object is
     *              {@link PRDEntities }
     */
    public void setPRDEntities(PRDEntities value) {
        this.prdEntities = value;
    }

    /**
     * Gets the value of the prdRules property.
     *
     * @return possible object is
     * {@link PRDRules }
     */
    public PRDRules getPRDRules() {
        return prdRules;
    }

    /**
     * Sets the value of the prdRules property.
     *
     * @param value allowed object is
     *              {@link PRDRules }
     */
    public void setPRDRules(PRDRules value) {
        this.prdRules = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sleep property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getSleep() {
        return sleep;
    }

    /**
     * Sets the value of the sleep property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setSleep(Integer value) {
        this.sleep = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="columns" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="rows" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class PRDGrid {

        @XmlAttribute(name = "columns", required = true)
        protected int columns;
        @XmlAttribute(name = "rows", required = true)
        protected int rows;

        /**
         * Gets the value of the columns property.
         */
        public int getColumns() {
            return columns;
        }

        /**
         * Sets the value of the columns property.
         */
        public void setColumns(int value) {
            this.columns = value;
        }

        /**
         * Gets the value of the rows property.
         */
        public int getRows() {
            return rows;
        }

        /**
         * Sets the value of the rows property.
         */
        public void setRows(int value) {
            this.rows = value;
        }

    }

}
