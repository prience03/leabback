package com.duolebo.appbase.prj;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by dengln on 16/4/23.
 */
public class XMLHelper {

    private XmlPullParser mXmlPullParser;

    public XMLHelper(String json) throws XmlPullParserException {
        mXmlPullParser = Xml.newPullParser();
        mXmlPullParser.setInput(new StringReader(json));
    }

    public void setFeature(String name, boolean state) throws XmlPullParserException {
        mXmlPullParser.setFeature(name, state);
    }

    public boolean getFeature(String name) {
        return mXmlPullParser.getFeature(name);
    }

    public void setProperty(String name, Object value) throws XmlPullParserException {
        mXmlPullParser.setProperty(name, value);
    }

    public Object getProperty(String name) {
        return mXmlPullParser.getProperty(name);
    }

    public void setInput(Reader in) throws XmlPullParserException {
        mXmlPullParser.setInput(in);
    }

    public void setInput(InputStream inputStream, String inputEncoding) throws XmlPullParserException {
        mXmlPullParser.setInput(inputStream, inputEncoding);
    }

    public String getInputEncoding() {
        return mXmlPullParser.getInputEncoding();
    }

    public void defineEntityReplacementText(String entityName, String replacementText) throws XmlPullParserException {
        mXmlPullParser.defineEntityReplacementText(entityName, replacementText);
    }

    public int getNamespaceCount(int depth) throws XmlPullParserException {
        return mXmlPullParser.getNamespaceCount(depth);
    }

    public String getNamespacePrefix(int pos) throws XmlPullParserException {
        return mXmlPullParser.getNamespacePrefix(pos);
    }

    public String getNamespaceUri(int pos) throws XmlPullParserException {
        return mXmlPullParser.getNamespaceUri(pos);
    }

    public String getNamespace(String prefix) {
        return mXmlPullParser.getNamespace(prefix);
    }

    public int getDepth() {
        return getDepth();
    }

    public String getPositionDescription() {
        return mXmlPullParser.getPositionDescription();
    }

    public int getLineNumber() {
        return mXmlPullParser.getLineNumber();
    }

    public int getColumnNumber() {
        return mXmlPullParser.getColumnNumber();
    }

    public boolean isWhitespace() throws XmlPullParserException {
        return mXmlPullParser.isWhitespace();
    }

    public String getText() {
        return mXmlPullParser.getText();
    }

    public char[] getTextCharacters(int[] holderForStartAndLength) {
        return mXmlPullParser.getTextCharacters(holderForStartAndLength);
    }

    public String getNamespace() {
        return mXmlPullParser.getNamespace();
    }

    public String getName() {
        return mXmlPullParser.getName();
    }

    public String getPrefix() {
        return mXmlPullParser.getPrefix();
    }

    public boolean isEmptyElementTag() throws XmlPullParserException {
        return mXmlPullParser.isEmptyElementTag();
    }

    public int getAttributeCount() {
        return mXmlPullParser.getAttributeCount();
    }

    public String getAttributeNamespace(int index) {
        return mXmlPullParser.getAttributeNamespace(index);
    }

    public String getAttributeName(int index) {
        return mXmlPullParser.getAttributeName(index);
    }

    public String getAttributePrefix(int index) {
        return mXmlPullParser.getAttributePrefix(index);
    }

    public String getAttributeType(int index) {
        return mXmlPullParser.getAttributeType(index);
    }

    public boolean isAttributeDefault(int index) {
        return mXmlPullParser.isAttributeDefault(index);
    }

    public String getAttributeValue(int index) {
        return mXmlPullParser.getAttributeValue(index);
    }

    public String getAttributeValue(String namespace, String name) {
        return mXmlPullParser.getAttributeValue(namespace, name);
    }

    public int getEventType() throws XmlPullParserException {
        return mXmlPullParser.getEventType();
    }

    public int next() throws XmlPullParserException, IOException {
        return mXmlPullParser.next();
    }

    public int nextToken() throws XmlPullParserException, IOException {
        return nextToken();
    }

    public void require(int type, String namespace, String name) throws XmlPullParserException, IOException {
        mXmlPullParser.require(type, namespace, name);
    }

    public String nextText() throws XmlPullParserException, IOException {
        return mXmlPullParser.nextText();
    }

    public int nextTag() throws XmlPullParserException, IOException {
        return mXmlPullParser.nextTag();
    }

}
