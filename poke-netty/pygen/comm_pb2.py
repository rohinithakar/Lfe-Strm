# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: comm.proto

from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)




DESCRIPTOR = _descriptor.FileDescriptor(
  name='comm.proto',
  package='',
  serialized_pb='\n\ncomm.proto\"%\n\x06\x46inger\x12\x0e\n\x06number\x18\x01 \x02(\x05\x12\x0b\n\x03tag\x18\x02 \x02(\t\"j\n\tNameSpace\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0c\n\x04name\x18\x02 \x02(\t\x12\x0c\n\x04\x64\x65sc\x18\x03 \x01(\t\x12\x0f\n\x07\x63reated\x18\x04 \x01(\x03\x12\x15\n\rlast_modified\x18\x05 \x01(\x03\x12\r\n\x05owner\x18\x06 \x01(\t\"\x94\x01\n\x0cNameValueSet\x12)\n\tnode_type\x18\x01 \x02(\x0e\x32\x16.NameValueSet.NodeType\x12\x0c\n\x04name\x18\x02 \x01(\t\x12\r\n\x05value\x18\x03 \x01(\t\x12\x1b\n\x04node\x18\x04 \x03(\x0b\x32\r.NameValueSet\"\x1f\n\x08NodeType\x12\x08\n\x04NODE\x10\x01\x12\t\n\x05VALUE\x10\x02\"K\n\x08\x44ocument\x12\x12\n\nname_space\x18\x01 \x01(\t\x12\n\n\x02id\x18\x02 \x01(\x03\x12\x1f\n\x08\x64ocument\x18\x03 \x02(\x0b\x32\r.NameValueSet\"+\n\x0b\x44ocumentSet\x12\x1c\n\tdocuments\x18\x01 \x03(\x0b\x32\t.Document\"*\n\x0cNameSpaceSet\x12\x1a\n\x06spaces\x18\x01 \x03(\x0b\x32\n.NameSpace\"\xde\x03\n\x06Header\x12#\n\nrouting_id\x18\x01 \x02(\x0e\x32\x0f.Header.Routing\x12\x12\n\noriginator\x18\x02 \x02(\t\x12\x0b\n\x03tag\x18\x03 \x01(\t\x12\x0c\n\x04time\x18\x04 \x01(\x03\x12\'\n\nreply_code\x18\x05 \x01(\x0e\x32\x13.Header.ReplyStatus\x12\x11\n\treply_msg\x18\x06 \x01(\t\"\xd4\x01\n\x07Routing\x12\n\n\x06\x46INGER\x10\x02\x12\t\n\x05STATS\x10\x03\x12\x10\n\x0cNAMESPACEADD\x10\n\x12\x11\n\rNAMESPACELIST\x10\x0b\x12\x13\n\x0fNAMESPACEUPDATE\x10\x0c\x12\x13\n\x0fNAMESPACEREMOVE\x10\r\x12\n\n\x06\x44OCADD\x10\x14\x12\x0b\n\x07\x44OCFIND\x10\x15\x12\r\n\tDOCUPDATE\x10\x16\x12\r\n\tDOCREMOVE\x10\x17\x12\r\n\tIMGUPLOAD\x10\x32\x12\x0f\n\x0bIMGRETREIVE\x10\x33\x12\x0c\n\x08REGISTER\x10\x34\"m\n\x0bReplyStatus\x12\x0b\n\x07SUCCESS\x10\x01\x12\x0b\n\x07\x46\x41ILURE\x10\x02\x12\n\n\x06NOAUTH\x10\x03\x12\x0e\n\nMISSINGARG\x10\x04\x12\x10\n\x0cNOCONNECTION\x10\x05\x12\x16\n\x12SERVER_UNAVAILABLE\x10\x06\":\n\x08Register\x12\r\n\x05\x66name\x18\x01 \x02(\t\x12\r\n\x05lname\x18\x02 \x02(\t\x12\x10\n\x08password\x18\x03 \x02(\t\"\x86\x01\n\x05Image\x12\x13\n\x0b\x61\x63tualImage\x18\x01 \x02(\x0c\x12\r\n\x05title\x18\x02 \x01(\t\x12\x10\n\x08latitude\x18\x03 \x01(\x01\x12\x11\n\tlongitude\x18\x04 \x01(\x01\x12\x11\n\ttimestamp\x18\x05 \x01(\x03\x12\r\n\x05imgid\x18\x06 \x01(\t\x12\x12\n\nowneremail\x18\x07 \x01(\t\"#\n\x10UserImageRequest\x12\x0f\n\x07\x65mailid\x18\x01 \x02(\t\"&\n\x0eUserImageReply\x12\x14\n\x04imgs\x18\x01 \x03(\x0b\x32\x06.Image\"\xba\x01\n\x07Payload\x12\x0f\n\x07\x65mailid\x18\x01 \x02(\t\x12\x17\n\x06\x66inger\x18\x02 \x01(\x0b\x32\x07.Finger\x12\x16\n\x03\x64oc\x18\x03 \x01(\x0b\x32\t.Document\x12\x19\n\x05space\x18\x04 \x01(\x0b\x32\n.NameSpace\x12\x16\n\x03reg\x18\x05 \x01(\x0b\x32\t.Register\x12\x17\n\x07imageup\x18\x06 \x01(\x0b\x32\x06.Image\x12!\n\x06imgreq\x18\x07 \x01(\x0b\x32\x11.UserImageRequest\"\x9b\x01\n\x0cPayloadReply\x12\x17\n\x04\x64ocs\x18\x01 \x03(\x0b\x32\t.Document\x12\x1a\n\x06spaces\x18\x02 \x03(\x0b\x32\n.NameSpace\x12\x18\n\x05stats\x18\x03 \x01(\x0b\x32\t.Document\x12\x19\n\x06\x66inger\x18\x04 \x01(\x0b\x32\t.Document\x12!\n\x08imgreply\x18\x05 \x01(\x0b\x32\x0f.UserImageReply\":\n\x07Request\x12\x17\n\x06header\x18\x01 \x02(\x0b\x32\x07.Header\x12\x16\n\x04\x62ody\x18\x02 \x02(\x0b\x32\x08.Payload\"@\n\x08Response\x12\x17\n\x06header\x18\x01 \x02(\x0b\x32\x07.Header\x12\x1b\n\x04\x62ody\x18\x02 \x02(\x0b\x32\r.PayloadReply\",\n\tHeartbeat\x12\x0e\n\x06nodeId\x18\x01 \x02(\t\x12\x0f\n\x07timeRef\x18\x02 \x02(\x03\"\x95\x01\n\x07Network\x12\x0e\n\x06nodeId\x18\x01 \x02(\t\x12\x1f\n\x06\x61\x63tion\x18\x02 \x02(\x0e\x32\x0f.Network.Action\"Y\n\x06\x41\x63tion\x12\x0c\n\x08NODEJOIN\x10\x01\x12\r\n\tNODELEAVE\x10\x02\x12\x0c\n\x08NODEDEAD\x10\x03\x12\x07\n\x03MAP\x10\x37\x12\x0c\n\x08\x41NNOUNCE\x10\x38\x12\r\n\x08SHUTDOWN\x10\xe7\x07\"?\n\nManagement\x12\x17\n\x05graph\x18\x01 \x01(\x0b\x32\x08.Network\x12\x18\n\x04\x62\x65\x61t\x18\x02 \x01(\x0b\x32\n.HeartbeatB\x07\n\x03\x65yeH\x01')



_NAMEVALUESET_NODETYPE = _descriptor.EnumDescriptor(
  name='NodeType',
  full_name='NameValueSet.NodeType',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='NODE', index=0, number=1,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='VALUE', index=1, number=2,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=279,
  serialized_end=310,
)

_HEADER_ROUTING = _descriptor.EnumDescriptor(
  name='Routing',
  full_name='Header.Routing',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='FINGER', index=0, number=2,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='STATS', index=1, number=3,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='NAMESPACEADD', index=2, number=10,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='NAMESPACELIST', index=3, number=11,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='NAMESPACEUPDATE', index=4, number=12,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='NAMESPACEREMOVE', index=5, number=13,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='DOCADD', index=6, number=20,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='DOCFIND', index=7, number=21,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='DOCUPDATE', index=8, number=22,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='DOCREMOVE', index=9, number=23,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='IMGUPLOAD', index=10, number=50,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='IMGRETREIVE', index=11, number=51,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='REGISTER', index=12, number=52,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=634,
  serialized_end=846,
)

_HEADER_REPLYSTATUS = _descriptor.EnumDescriptor(
  name='ReplyStatus',
  full_name='Header.ReplyStatus',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='SUCCESS', index=0, number=1,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='FAILURE', index=1, number=2,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='NOAUTH', index=2, number=3,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='MISSINGARG', index=3, number=4,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='NOCONNECTION', index=4, number=5,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='SERVER_UNAVAILABLE', index=5, number=6,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=848,
  serialized_end=957,
)

_NETWORK_ACTION = _descriptor.EnumDescriptor(
  name='Action',
  full_name='Network.Action',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='NODEJOIN', index=0, number=1,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='NODELEAVE', index=1, number=2,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='NODEDEAD', index=2, number=3,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='MAP', index=3, number=55,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='ANNOUNCE', index=4, number=56,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='SHUTDOWN', index=5, number=999,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=1813,
  serialized_end=1902,
)


_FINGER = _descriptor.Descriptor(
  name='Finger',
  full_name='Finger',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='number', full_name='Finger.number', index=0,
      number=1, type=5, cpp_type=1, label=2,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='tag', full_name='Finger.tag', index=1,
      number=2, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=14,
  serialized_end=51,
)


_NAMESPACE = _descriptor.Descriptor(
  name='NameSpace',
  full_name='NameSpace',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='id', full_name='NameSpace.id', index=0,
      number=1, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='name', full_name='NameSpace.name', index=1,
      number=2, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='desc', full_name='NameSpace.desc', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='created', full_name='NameSpace.created', index=3,
      number=4, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='last_modified', full_name='NameSpace.last_modified', index=4,
      number=5, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='owner', full_name='NameSpace.owner', index=5,
      number=6, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=53,
  serialized_end=159,
)


_NAMEVALUESET = _descriptor.Descriptor(
  name='NameValueSet',
  full_name='NameValueSet',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='node_type', full_name='NameValueSet.node_type', index=0,
      number=1, type=14, cpp_type=8, label=2,
      has_default_value=False, default_value=1,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='name', full_name='NameValueSet.name', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='value', full_name='NameValueSet.value', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='node', full_name='NameValueSet.node', index=3,
      number=4, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
    _NAMEVALUESET_NODETYPE,
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=162,
  serialized_end=310,
)


_DOCUMENT = _descriptor.Descriptor(
  name='Document',
  full_name='Document',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='name_space', full_name='Document.name_space', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='id', full_name='Document.id', index=1,
      number=2, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='document', full_name='Document.document', index=2,
      number=3, type=11, cpp_type=10, label=2,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=312,
  serialized_end=387,
)


_DOCUMENTSET = _descriptor.Descriptor(
  name='DocumentSet',
  full_name='DocumentSet',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='documents', full_name='DocumentSet.documents', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=389,
  serialized_end=432,
)


_NAMESPACESET = _descriptor.Descriptor(
  name='NameSpaceSet',
  full_name='NameSpaceSet',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='spaces', full_name='NameSpaceSet.spaces', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=434,
  serialized_end=476,
)


_HEADER = _descriptor.Descriptor(
  name='Header',
  full_name='Header',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='routing_id', full_name='Header.routing_id', index=0,
      number=1, type=14, cpp_type=8, label=2,
      has_default_value=False, default_value=2,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='originator', full_name='Header.originator', index=1,
      number=2, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='tag', full_name='Header.tag', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='time', full_name='Header.time', index=3,
      number=4, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='reply_code', full_name='Header.reply_code', index=4,
      number=5, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=1,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='reply_msg', full_name='Header.reply_msg', index=5,
      number=6, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
    _HEADER_ROUTING,
    _HEADER_REPLYSTATUS,
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=479,
  serialized_end=957,
)


_REGISTER = _descriptor.Descriptor(
  name='Register',
  full_name='Register',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='fname', full_name='Register.fname', index=0,
      number=1, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='lname', full_name='Register.lname', index=1,
      number=2, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='password', full_name='Register.password', index=2,
      number=3, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=959,
  serialized_end=1017,
)


_IMAGE = _descriptor.Descriptor(
  name='Image',
  full_name='Image',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='actualImage', full_name='Image.actualImage', index=0,
      number=1, type=12, cpp_type=9, label=2,
      has_default_value=False, default_value="",
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='title', full_name='Image.title', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='latitude', full_name='Image.latitude', index=2,
      number=3, type=1, cpp_type=5, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='longitude', full_name='Image.longitude', index=3,
      number=4, type=1, cpp_type=5, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='timestamp', full_name='Image.timestamp', index=4,
      number=5, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='imgid', full_name='Image.imgid', index=5,
      number=6, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='owneremail', full_name='Image.owneremail', index=6,
      number=7, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1020,
  serialized_end=1154,
)


_USERIMAGEREQUEST = _descriptor.Descriptor(
  name='UserImageRequest',
  full_name='UserImageRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='emailid', full_name='UserImageRequest.emailid', index=0,
      number=1, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1156,
  serialized_end=1191,
)


_USERIMAGEREPLY = _descriptor.Descriptor(
  name='UserImageReply',
  full_name='UserImageReply',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='imgs', full_name='UserImageReply.imgs', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1193,
  serialized_end=1231,
)


_PAYLOAD = _descriptor.Descriptor(
  name='Payload',
  full_name='Payload',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='emailid', full_name='Payload.emailid', index=0,
      number=1, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='finger', full_name='Payload.finger', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='doc', full_name='Payload.doc', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='space', full_name='Payload.space', index=3,
      number=4, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='reg', full_name='Payload.reg', index=4,
      number=5, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='imageup', full_name='Payload.imageup', index=5,
      number=6, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='imgreq', full_name='Payload.imgreq', index=6,
      number=7, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1234,
  serialized_end=1420,
)


_PAYLOADREPLY = _descriptor.Descriptor(
  name='PayloadReply',
  full_name='PayloadReply',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='docs', full_name='PayloadReply.docs', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='spaces', full_name='PayloadReply.spaces', index=1,
      number=2, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='stats', full_name='PayloadReply.stats', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='finger', full_name='PayloadReply.finger', index=3,
      number=4, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='imgreply', full_name='PayloadReply.imgreply', index=4,
      number=5, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1423,
  serialized_end=1578,
)


_REQUEST = _descriptor.Descriptor(
  name='Request',
  full_name='Request',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='header', full_name='Request.header', index=0,
      number=1, type=11, cpp_type=10, label=2,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='body', full_name='Request.body', index=1,
      number=2, type=11, cpp_type=10, label=2,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1580,
  serialized_end=1638,
)


_RESPONSE = _descriptor.Descriptor(
  name='Response',
  full_name='Response',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='header', full_name='Response.header', index=0,
      number=1, type=11, cpp_type=10, label=2,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='body', full_name='Response.body', index=1,
      number=2, type=11, cpp_type=10, label=2,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1640,
  serialized_end=1704,
)


_HEARTBEAT = _descriptor.Descriptor(
  name='Heartbeat',
  full_name='Heartbeat',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='nodeId', full_name='Heartbeat.nodeId', index=0,
      number=1, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='timeRef', full_name='Heartbeat.timeRef', index=1,
      number=2, type=3, cpp_type=2, label=2,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1706,
  serialized_end=1750,
)


_NETWORK = _descriptor.Descriptor(
  name='Network',
  full_name='Network',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='nodeId', full_name='Network.nodeId', index=0,
      number=1, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='action', full_name='Network.action', index=1,
      number=2, type=14, cpp_type=8, label=2,
      has_default_value=False, default_value=1,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
    _NETWORK_ACTION,
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1753,
  serialized_end=1902,
)


_MANAGEMENT = _descriptor.Descriptor(
  name='Management',
  full_name='Management',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='graph', full_name='Management.graph', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='beat', full_name='Management.beat', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=1904,
  serialized_end=1967,
)

_NAMEVALUESET.fields_by_name['node_type'].enum_type = _NAMEVALUESET_NODETYPE
_NAMEVALUESET.fields_by_name['node'].message_type = _NAMEVALUESET
_NAMEVALUESET_NODETYPE.containing_type = _NAMEVALUESET;
_DOCUMENT.fields_by_name['document'].message_type = _NAMEVALUESET
_DOCUMENTSET.fields_by_name['documents'].message_type = _DOCUMENT
_NAMESPACESET.fields_by_name['spaces'].message_type = _NAMESPACE
_HEADER.fields_by_name['routing_id'].enum_type = _HEADER_ROUTING
_HEADER.fields_by_name['reply_code'].enum_type = _HEADER_REPLYSTATUS
_HEADER_ROUTING.containing_type = _HEADER;
_HEADER_REPLYSTATUS.containing_type = _HEADER;
_USERIMAGEREPLY.fields_by_name['imgs'].message_type = _IMAGE
_PAYLOAD.fields_by_name['finger'].message_type = _FINGER
_PAYLOAD.fields_by_name['doc'].message_type = _DOCUMENT
_PAYLOAD.fields_by_name['space'].message_type = _NAMESPACE
_PAYLOAD.fields_by_name['reg'].message_type = _REGISTER
_PAYLOAD.fields_by_name['imageup'].message_type = _IMAGE
_PAYLOAD.fields_by_name['imgreq'].message_type = _USERIMAGEREQUEST
_PAYLOADREPLY.fields_by_name['docs'].message_type = _DOCUMENT
_PAYLOADREPLY.fields_by_name['spaces'].message_type = _NAMESPACE
_PAYLOADREPLY.fields_by_name['stats'].message_type = _DOCUMENT
_PAYLOADREPLY.fields_by_name['finger'].message_type = _DOCUMENT
_PAYLOADREPLY.fields_by_name['imgreply'].message_type = _USERIMAGEREPLY
_REQUEST.fields_by_name['header'].message_type = _HEADER
_REQUEST.fields_by_name['body'].message_type = _PAYLOAD
_RESPONSE.fields_by_name['header'].message_type = _HEADER
_RESPONSE.fields_by_name['body'].message_type = _PAYLOADREPLY
_NETWORK.fields_by_name['action'].enum_type = _NETWORK_ACTION
_NETWORK_ACTION.containing_type = _NETWORK;
_MANAGEMENT.fields_by_name['graph'].message_type = _NETWORK
_MANAGEMENT.fields_by_name['beat'].message_type = _HEARTBEAT
DESCRIPTOR.message_types_by_name['Finger'] = _FINGER
DESCRIPTOR.message_types_by_name['NameSpace'] = _NAMESPACE
DESCRIPTOR.message_types_by_name['NameValueSet'] = _NAMEVALUESET
DESCRIPTOR.message_types_by_name['Document'] = _DOCUMENT
DESCRIPTOR.message_types_by_name['DocumentSet'] = _DOCUMENTSET
DESCRIPTOR.message_types_by_name['NameSpaceSet'] = _NAMESPACESET
DESCRIPTOR.message_types_by_name['Header'] = _HEADER
DESCRIPTOR.message_types_by_name['Register'] = _REGISTER
DESCRIPTOR.message_types_by_name['Image'] = _IMAGE
DESCRIPTOR.message_types_by_name['UserImageRequest'] = _USERIMAGEREQUEST
DESCRIPTOR.message_types_by_name['UserImageReply'] = _USERIMAGEREPLY
DESCRIPTOR.message_types_by_name['Payload'] = _PAYLOAD
DESCRIPTOR.message_types_by_name['PayloadReply'] = _PAYLOADREPLY
DESCRIPTOR.message_types_by_name['Request'] = _REQUEST
DESCRIPTOR.message_types_by_name['Response'] = _RESPONSE
DESCRIPTOR.message_types_by_name['Heartbeat'] = _HEARTBEAT
DESCRIPTOR.message_types_by_name['Network'] = _NETWORK
DESCRIPTOR.message_types_by_name['Management'] = _MANAGEMENT

class Finger(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _FINGER

  # @@protoc_insertion_point(class_scope:Finger)

class NameSpace(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _NAMESPACE

  # @@protoc_insertion_point(class_scope:NameSpace)

class NameValueSet(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _NAMEVALUESET

  # @@protoc_insertion_point(class_scope:NameValueSet)

class Document(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _DOCUMENT

  # @@protoc_insertion_point(class_scope:Document)

class DocumentSet(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _DOCUMENTSET

  # @@protoc_insertion_point(class_scope:DocumentSet)

class NameSpaceSet(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _NAMESPACESET

  # @@protoc_insertion_point(class_scope:NameSpaceSet)

class Header(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _HEADER

  # @@protoc_insertion_point(class_scope:Header)

class Register(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _REGISTER

  # @@protoc_insertion_point(class_scope:Register)

class Image(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _IMAGE

  # @@protoc_insertion_point(class_scope:Image)

class UserImageRequest(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _USERIMAGEREQUEST

  # @@protoc_insertion_point(class_scope:UserImageRequest)

class UserImageReply(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _USERIMAGEREPLY

  # @@protoc_insertion_point(class_scope:UserImageReply)

class Payload(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _PAYLOAD

  # @@protoc_insertion_point(class_scope:Payload)

class PayloadReply(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _PAYLOADREPLY

  # @@protoc_insertion_point(class_scope:PayloadReply)

class Request(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _REQUEST

  # @@protoc_insertion_point(class_scope:Request)

class Response(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _RESPONSE

  # @@protoc_insertion_point(class_scope:Response)

class Heartbeat(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _HEARTBEAT

  # @@protoc_insertion_point(class_scope:Heartbeat)

class Network(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _NETWORK

  # @@protoc_insertion_point(class_scope:Network)

class Management(_message.Message):
  __metaclass__ = _reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _MANAGEMENT

  # @@protoc_insertion_point(class_scope:Management)


DESCRIPTOR.has_options = True
DESCRIPTOR._options = _descriptor._ParseOptions(descriptor_pb2.FileOptions(), '\n\003eyeH\001')
# @@protoc_insertion_point(module_scope)
