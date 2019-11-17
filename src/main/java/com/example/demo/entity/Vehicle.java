package com.example.demo.entity;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@Document(indexName = "rider", type = "vehicle")
@CsvRecord(separator = "," , skipFirstLine = true)
@Document(indexName = "rider1", type = "vehicle")
public class Vehicle {

	@Id
	private String id;
	
	@DataField(pos = 1)
	private String vinPrefix;
	@DataField(pos = 2)
	private String vinModelYearChar;
	@DataField(pos = 3)
	private int modelYear;
	@DataField(pos = 4)
	private int makeCode;
	@DataField(pos = 5)
	private String makeName;
	@DataField(pos = 6)
	private String modelDesc;
	@DataField(pos = 7)
	private String seriesName;
	@DataField(pos = 8)
	private int displacement;
	@DataField(pos = 9)
	private String mcClassName;
	@DataField(pos = 10)
	private String engineType;
	@DataField(pos = 11)
	private double hp;
	@DataField(pos = 12)
	private double torque;
	@DataField(pos = 13)
	private String drive;
	@DataField(pos = 14)
	private String transmission;
	@DataField(pos = 15)
	private String dryWeight;
	@DataField(pos = 16)
	private String wheelbase;
	@DataField(pos = 17)
	private int retailPrice;
	@DataField(pos = 18)
	private String absEquipped;
	@DataField(pos = 19)
	private String exhaustName;

	public Vehicle() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVinPrefix() {
		return vinPrefix;
	}

	public void setVinPrefix(String vinPrefix) {
		this.vinPrefix = vinPrefix;
	}

	public String getVinModelYearChar() {
		return vinModelYearChar;
	}

	public void setVinModelYearChar(String vinModelYearChar) {
		this.vinModelYearChar = vinModelYearChar;
	}

	public int getModelYear() {
		return modelYear;
	}

	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}

	public int getMakeCode() {
		return makeCode;
	}

	public void setMakeCode(int makeCode) {
		this.makeCode = makeCode;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {		
		this.makeName = makeName;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public int getDisplacement() {
		return displacement;
	}

	public void setDisplacement(int displacement) {
		this.displacement = displacement;
	}

	public String getMcClassName() {
		return mcClassName;
	}

	public void setMcClassName(String mcClassName) {
		this.mcClassName = mcClassName;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getTorque() {
		return torque;
	}

	public void setTorque(double torque) {
		this.torque = torque;
	}

	public String getDrive() {
		return drive;
	}

	public void setDrive(String drive) {
		this.drive = drive;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getDryWeight() {
		return dryWeight;
	}

	public void setDryWeight(String dryWeight) {
		this.dryWeight = dryWeight;
	}

	public String getWheelbase() {
		return wheelbase;
	}

	public void setWheelbase(String wheelbase) {
		this.wheelbase = wheelbase;
	}

	public int getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(int retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getAbsEquipped() {
		return absEquipped;
	}

	public void setAbsEquipped(String absEquipped) {
		this.absEquipped = absEquipped;
	}

	public String getExhaustName() {
		return exhaustName;
	}

	public void setExhaustName(String exhaustName) {
		this.exhaustName = exhaustName;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", vinPrefix=" + vinPrefix + ", vinModelYearChar=" + vinModelYearChar
				+ ", modelYear=" + modelYear + ", makeCode=" + makeCode + ", makeName=" + makeName + ", modelDesc="
				+ modelDesc + ", seriesName=" + seriesName + ", displacement=" + displacement + ", mcClassName="
				+ mcClassName + ", engineType=" + engineType + ", hp=" + hp + ", torque=" + torque + ", drive=" + drive
				+ ", transmission=" + transmission + ", dryWeight=" + dryWeight + ", wheelbase=" + wheelbase
				+ ", retailPrice=" + retailPrice + ", absEquipped=" + absEquipped + ", exhaustName=" + exhaustName
				+ "]";
	}

	

}
