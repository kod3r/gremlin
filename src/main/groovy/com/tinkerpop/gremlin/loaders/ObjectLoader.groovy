package com.tinkerpop.gremlin.loaders

import com.tinkerpop.gremlin.Gremlin
import com.tinkerpop.pipes.IdentityPipe

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
class ObjectLoader {

  public static void load() {

    Object.metaClass.propertyMissing = {final String name ->
      if (Gremlin.getMissingMethods(delegate.getClass()).contains(name)) {
        return delegate."$name"();
      } else {
        throw new MissingPropertyException(name, delegate.getClass());
      }
    }

    // PIPES

    Object.metaClass._ = {final Closure closure ->
      return Gremlin.compose(delegate, new IdentityPipe(), closure)
    }
  }
}