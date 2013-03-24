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
import poke.db.IImageStorage;
import poke.db.StorageFactory;
import poke.server.conf.ServerConf;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;
import eye.Comm.Header.ReplyStatus;
import eye.Comm.Image;
import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;

public class ImageUploadResource implements Resource {
	protected static Logger logger = LoggerFactory.getLogger("server");
	private ServerConf.GeneralConf param;
	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.resources.Resource#process(eye.Comm.Finger)
	 */
	public Response process(Request request) {

		String email = request.getBody().getEmailid();
		Image img = request.getBody().getImageup();

		IImageStorage imageStorage = StorageFactory.getImageStorage(this.param);
		Response.Builder r = Response.newBuilder();
		try {
			if(imageStorage.storeImage(email, img)){
				r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
						ReplyStatus.SUCCESS, null));
			}else{
				r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
						ReplyStatus.FAILURE, null));
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
					ReplyStatus.FAILURE, null));
		}

		eye.Comm.PayloadReply.Builder br=PayloadReply.newBuilder();

		r.setBody(br.build());

		Response reply = r.build();
		logger.info("Hellooo sweta");

		return reply;
	}

	@Override
	public void init(ServerConf.GeneralConf param) {
		this.param = param;
	}
}
