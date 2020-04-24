/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\dx\\remote\\remote_master\\logapplication\\src\\main\\aidl\\com\\dx\\log\\logapplication\\service\\ILogRemoteAidlInterface.aidl
 */
package com.dx.log.logapplication.service;
// Declare any non-default types here with import statements

public interface ILogRemoteAidlInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements ILogRemoteAidlInterface
{
private static final String DESCRIPTOR = "com.dx.log.logapplication.service.ILogRemoteAidlInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.dx.log.logapplication.service.ILogRemoteAidlInterface interface,
 * generating a proxy if needed.
 */
public static ILogRemoteAidlInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof ILogRemoteAidlInterface))) {
return ((ILogRemoteAidlInterface)iin);
}
return new Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_println:
{
data.enforceInterface(descriptor);
CharSequence _arg0;
if ((0!=data.readInt())) {
_arg0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
CharSequence _arg1;
if ((0!=data.readInt())) {
_arg1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
CharSequence _arg2;
if ((0!=data.readInt())) {
_arg2 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
CharSequence _arg3;
if ((0!=data.readInt())) {
_arg3 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
this.println(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_d:
{
data.enforceInterface(descriptor);
CharSequence _arg0;
if ((0!=data.readInt())) {
_arg0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
CharSequence _arg1;
if ((0!=data.readInt())) {
_arg1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.d(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_printlnTag:
{
data.enforceInterface(descriptor);
CharSequence _arg0;
if ((0!=data.readInt())) {
_arg0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
CharSequence _arg1;
if ((0!=data.readInt())) {
_arg1 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
CharSequence _arg2;
if ((0!=data.readInt())) {
_arg2 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
CharSequence _arg3;
if ((0!=data.readInt())) {
_arg3 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
this.printlnTag(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_showWindow:
{
data.enforceInterface(descriptor);
this.showWindow();
reply.writeNoException();
return true;
}
case TRANSACTION_hideWindow:
{
data.enforceInterface(descriptor);
this.hideWindow();
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements ILogRemoteAidlInterface
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void println(CharSequence tag, CharSequence url, CharSequence parms, CharSequence response) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((tag!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(tag, _data, 0);
}
else {
_data.writeInt(0);
}
if ((url!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(url, _data, 0);
}
else {
_data.writeInt(0);
}
if ((parms!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(parms, _data, 0);
}
else {
_data.writeInt(0);
}
if ((response!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(response, _data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_println, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void d(CharSequence tag, CharSequence msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((tag!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(tag, _data, 0);
}
else {
_data.writeInt(0);
}
if ((msg!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(msg, _data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_d, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void printlnTag(CharSequence packageName, CharSequence tag, CharSequence childTag, CharSequence msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((packageName!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(packageName, _data, 0);
}
else {
_data.writeInt(0);
}
if ((tag!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(tag, _data, 0);
}
else {
_data.writeInt(0);
}
if ((childTag!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(childTag, _data, 0);
}
else {
_data.writeInt(0);
}
if ((msg!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(msg, _data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_printlnTag, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void showWindow() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_showWindow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void hideWindow() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_hideWindow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_println = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_d = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_printlnTag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_showWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_hideWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void println(CharSequence tag, CharSequence url, CharSequence parms, CharSequence response) throws android.os.RemoteException;
public void d(CharSequence tag, CharSequence msg) throws android.os.RemoteException;
public void printlnTag(CharSequence packageName, CharSequence tag, CharSequence childTag, CharSequence msg) throws android.os.RemoteException;
public void showWindow() throws android.os.RemoteException;
public void hideWindow() throws android.os.RemoteException;
}
