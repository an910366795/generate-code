package ${package.entityPath};

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
<#list table.typeList as type>
    <#if type == 'BigDecimal' >
import java.math.BigDecimal;
    </#if>
    <#if type == 'Date'>
import java.util.Date;
    </#if>
    <#if type == 'Blob'>
import java.sql.Blob;
    </#if>
    <#if type == 'Clob'>
import java.sql.Clob;
    </#if>
</#list>

/**
 * @Description:表${table.tableName}对应的实体类
 * @Author: ${author}
 * @Date: ${now}
 */
@Table(name = "${table.tableName}")
@Getter
@Setter
@ToString
public class ${entityName} {

<#list fieldList as field>
    <#if field.key>
    /**
     * ${field.fieldComment}
     */
    @Id
    @Column(name = "${field.filedName}")
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
<#list fieldList as field>
    <#if !field.key>
    /**
     * ${field.fieldComment}
     */
    @Column(name = "${field.filedName}")
    private ${field.propertyType} ${field.propertyName};
    </#if>

</#list>

<#--<#list fieldList as field>
    public ${field.propertyType} get${field.propertyName ? cap_first}() {
        return ${field.propertyName};
    }

    public void set${field.propertyName? cap_first}(${field.propertyType} ${field.propertyName}){
        this.${field.propertyName} = ${field.propertyName};
    }
</#list>-->


}
