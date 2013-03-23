/*
 * copyright 2012, gash
 * 
 * Gash licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package poke.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.db.DBException;
import poke.db.StorageFactory;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;
import eye.Comm.Header.ReplyStatus;
import eye.Comm.Image;
import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;

public class ImageUploadResource implements Resource {
	protected static Logger logger = LoggerFactory.getLogger("server");

	public ImageUploadResource() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.resources.Resource#process(eye.Comm.Finger)
	 */
	public Response process(Request request) {

		String email = request.getBody().getEmailid();
		Image img = request.getBody().getImageup();

		try {
			StorageFactory.getStorage().storeImage(email, img);
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Response.Builder r = Response.newBuilder();
		r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
				ReplyStatus.SUCCESS, null));

		eye.Comm.PayloadReply.Builder br=PayloadReply.newBuilder();

		r.setBody(br.build());

		Response reply = r.build();
		logger.info("Hellooo sweta");

		return reply;
	}

	@Override
	public void init(String param) {
		// TODO Auto-generated method stub

	}
}
