package com.ogham_stone.example.code.redislua.app.point;

public class PointRequest
{
	private Double neLatitude;
	private Double neLongitude;
	private Double swLatitude;
	private Double swLongitude;

	public Double getNeLatitude()
	{
		return neLatitude;
	}

	public void setNeLatitude(Double neLatitude)
	{
		this.neLatitude = neLatitude;
	}

	public Double getNeLongitude()
	{
		return neLongitude;
	}

	public void setNeLongitude(Double neLongitude)
	{
		this.neLongitude = neLongitude;
	}

	public Double getSwLatitude()
	{
		return swLatitude;
	}

	public void setSwLatitude(Double swLatitude)
	{
		this.swLatitude = swLatitude;
	}

	public Double getSwLongitude()
	{
		return swLongitude;
	}

	public void setSwLongitude(Double swLongitude)
	{
		this.swLongitude = swLongitude;
	}
}
