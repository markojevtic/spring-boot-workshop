package pd.workshop.domain.impl;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import pd.workshop.domain.LockDemoRepositoryCustom;
import pd.workshop.domain.model.LockDemo;

public class LockDemoRepositoryImpl implements LockDemoRepositoryCustom {
    @Autowired
    private MongoTemplate template;

    @Override
    public int updateText(Long id, String newText) {
        Update update = new Update();
        update.set( "text", newText );
        return template.updateFirst( new Query( where( "id" ).is( id ) ), update, LockDemo.class ).getN();
    }
}
