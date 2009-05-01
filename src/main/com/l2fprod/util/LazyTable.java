/**
 * @PROJECT.FULLNAME@ @VERSION@ License.
 *
 * Copyright @YEAR@ L2FProd.com
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
package com.l2fprod.util;

import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class LazyTable extends Hashtable {

  private final static Object PENDING = new String("Pending");

  /**
   * Description of the Method
   *
   * @param key  Description of Parameter
   * @return     Description of the Returned Value
   */
  public Object get(Object key) {
    /*
     *  Quickly handle the common case, without grabbing
     *  a lock.
     */
    Object value = super.get(key);
    if ((value != PENDING) &&
        !(value instanceof ActiveValue) &&
        !(value instanceof LazyValue)) {
      return value;
    }

    /*
     *  If the LazyValue for key is being constructed by another
     *  thread then wait and then return the new value, otherwise drop
     *  the lock and construct the ActiveValue or the LazyValue.
     *  We use the special value PENDING to mark LazyValues that
     *  are being constructed.
     */
    synchronized (this) {
      value = super.get(key);
      if (value == PENDING) {
        do {
          try {
            this.wait();
          } catch (InterruptedException e) {
          }
          value = super.get(key);
        } while (value == PENDING);
        return value;
      }
      else if (value instanceof LazyValue) {
        super.put(key, PENDING);
      }
      else if (!(value instanceof ActiveValue)) {
        return value;
      }
    }

    /*
     *  At this point we know that the value of key was
     *  a LazyValue or an ActiveValue.
     */
    if (value instanceof LazyValue) {
      try {
        /*
         *  If an exception is thrown we'll just put the LazyValue
         *  back in the table.
         */
        value = ((LazyValue) value).createValue(this);
      } finally {
        synchronized (this) {
          if (value == null) {
            super.remove(key);
          }
          else {
            super.put(key, value);
          }
          this.notify();
        }
      }
    }
    else {
      value = ((ActiveValue) value).createValue(this);
    }

    return value;
  }

  /**
   * Description of the Interface
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public interface LazyValue {
    /**
     * Description of the Method
     *
     * @param table  Description of Parameter
     * @return       Description of the Returned Value
     */
    Object createValue(LazyTable table);
  }

  /**
   * Description of the Interface
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public interface ActiveValue {
    /**
     * Description of the Method
     *
     * @param table  Description of Parameter
     * @return       Description of the Returned Value
     */
    Object createValue(LazyTable table);
  }
}

