/*
 * Druid - a distributed column store.
 * Copyright 2012 - 2015 Metamarkets Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.druid.query.aggregation;

import io.druid.segment.FloatColumnSelector;

import java.util.Comparator;

/**
 */
public class MaxAggregator implements Aggregator
{
  static final Comparator COMPARATOR = DoubleSumAggregator.COMPARATOR;

  static double combineValues(Object lhs, Object rhs)
  {
    return Math.max(((Number) lhs).doubleValue(), ((Number) rhs).doubleValue());
  }

  private final FloatColumnSelector selector;
  private final String name;

  private double max;

  public MaxAggregator(String name, FloatColumnSelector selector)
  {
    this.name = name;
    this.selector = selector;

    reset();
  }

  @Override
  public void aggregate()
  {
    max = Math.max(max, selector.get());
  }

  @Override
  public void reset()
  {
    max = Double.NEGATIVE_INFINITY;
  }

  @Override
  public Object get()
  {
    return max;
  }

  @Override
  public float getFloat()
  {
    return (float) max;
  }

  @Override
  public long getLong()
  {
    return (long) max;
  }

  @Override
  public String getName()
  {
    return this.name;
  }

  @Override
  public Aggregator clone()
  {
    return new MaxAggregator(name, selector);
  }

  @Override
  public void close()
  {
    // no resources to cleanup
  }
}
