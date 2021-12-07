package com.example.kafkainternapp.config;

import com.example.kafkainternapp.entities.ConsumedEntity;
import com.example.kafkainternapp.entities.ProducedEntity;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Table;

@Configuration
public class TableNameStrategy extends CamelCaseToUnderscoresNamingStrategy {
    @Value("${app_consumed_entity_table_name}")
    private String consumedEntityTableName;
    @Value("${app_produced_entity_table_name}")
    private String producedEntityTableName;

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String producedEntityLogicalName = ProducedEntity.class.getAnnotation(Table.class).name();
        String consumedEntityLogicalName = ConsumedEntity.class.getAnnotation(Table.class).name();

        if (producedEntityLogicalName.equals(name.getText())) {
            return Identifier.toIdentifier(producedEntityTableName);
        } else if (consumedEntityLogicalName.equals(name.getText())) {
            return Identifier.toIdentifier(consumedEntityTableName);
        }
        return super.toPhysicalTableName(name, context);
    }
}
