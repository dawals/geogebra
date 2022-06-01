package org.geogebra.common.kernel.interval;

import java.util.Collections;
import java.util.List;

import org.geogebra.common.kernel.arithmetic.ExpressionNode;
import org.geogebra.common.plugin.Operation;

public class IfSampler implements ExpressionSampler {


	@Override
	public boolean isAccepted(ExpressionNode node) {
		return node != null && Operation.IF.equals(node.getOperation());
	}

	@Override
	public MultiSampler create(ExpressionNode node) {
		IntervalConditionalExpression expression =
				new IntervalConditionalExpression(node.getLeftTree(), node.getRightTree());
		List<ConditionalSampler> samplers =
				Collections.singletonList(new ConditionalSampler(expression));
		IntervalEvaluatable evaluator =
				new DefaultConditionalEvaluator(samplers);
		return new MultiSampler(evaluator, samplers);
	}

}
