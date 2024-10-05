package codezap.template.repository;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;

import codezap.template.domain.QSourceCode;
import codezap.template.domain.QTemplate;
import codezap.template.domain.QTemplateTag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QTemplateSpecification {
    private final QTemplate template = QTemplate.template;
    private final QSourceCode sourceCode = QSourceCode.sourceCode;
    private final QTemplateTag templateTag = QTemplateTag.templateTag;

    private final Long memberId;
    private final String keyword;
    private final Long categoryId;
    private final List<Long> tagIds;

    public Predicate build() {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(memberId != null ? template.member.id.eq(memberId) : null)
                .and(categoryId != null ? template.category.id.eq(categoryId) : null)
                .and(tagPredicate())
                .and(keywordPredicate());

        return builder.getValue();
    }

    private BooleanExpression tagPredicate() {
        if (tagIds == null || tagIds.isEmpty()) {
            return null;
        }
        return template.id.in(
                JPAExpressions.select(templateTag.template.id)
                        .from(templateTag)
                        .where(templateTag.tag.id.in(tagIds))
                        .groupBy(templateTag.template.id)
                        .having(templateTag.tag.id.countDistinct().eq((long) tagIds.size()))
        );
    }

    private BooleanExpression keywordPredicate() {
        String searchKeyword = checkKeyword(keyword);
        BooleanExpression titleDescriptionMatch =
                template.title.concat(" ").concat(template.description).containsIgnoreCase(searchKeyword);

        BooleanExpression sourceCodeMatch =
                template.id.in(
                        JPAExpressions.select(sourceCode.template.id)
                                .from(sourceCode)
                                .where(sourceCode.content.concat(" ").concat(sourceCode.filename).containsIgnoreCase(searchKeyword))
                );

        return titleDescriptionMatch.or(sourceCodeMatch);
    }

    private String checkKeyword(String keyword) {
        return keyword == null || keyword.trim().isEmpty() ? null : keyword.trim();
    }
}
