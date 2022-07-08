package ai.aitia.arrowhead.application.library.util;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import eu.arrowhead.common.CommonConstants;

@Component(CommonConstants.ARROWHEAD_CONTEXT)
public class ArrowheadContext extends ConcurrentHashMap<String,Object> {

	private static final long serialVersionUID = -4506616943869170913L;
}
